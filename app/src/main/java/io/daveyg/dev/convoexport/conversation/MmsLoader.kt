package io.daveyg.dev.convoexport.conversation

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.provider.Telephony
import android.provider.Telephony.TextBasedSmsColumns.MESSAGE_TYPE_INBOX
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset


class MmsLoader(var contentResolver: ContentResolver) : IMessageLoader {

    override fun loadMessages(): List<IMessage> {
        val messages: ArrayList<IMessage> = ArrayList<IMessage>()
        val uri: Uri = Telephony.Mms.CONTENT_URI
        val cursor: Cursor? = contentResolver.query(
            uri,
            null,
            "thread_id IS NOT NULL) GROUP BY (thread_id",
            null,
            null
        )
        cursor?.use {
            while (it.moveToNext()) {
                val threadId: Int = it.getInt(it.getColumnIndex("thread_id"))
                val message: List<IMessage> = getMessagesFromThreadId(threadId)
                messages.addAll(message)
            }
        }
        return messages
    }


    private fun getMessagesFromThreadId(threadId: Int): List<IMessage> {
        val messages: ArrayList<IMessage> = ArrayList<IMessage>()
        val projection: Array<String> = arrayOf("_id", "thread_id", "date", "m_type")
        val selectionVar: Array<String> = arrayOf(threadId.toString())
        val uri: Uri = Telephony.Mms.CONTENT_URI
        val cursor: Cursor? =
            contentResolver.query(uri, projection, "thread_id=?", selectionVar, null)
        cursor?.use {
            while(it.moveToNext()){
                val id : Int = it.getInt(it.getColumnIndex(Telephony.Mms.MESSAGE_ID))
                val message : MmsMessage = MmsMessage(threadId)
                message.body = getMessageText(id)
                message.address = getMessageAddress(id)
                message.name = ContactAddressLookup.getContactNameFromNumber(contentResolver, message.address)
                message.date = LocalDateTime.ofInstant(Instant.ofEpochMilli(it.getLong(it.getColumnIndex(Telephony.Mms.DATE))), ZoneOffset.systemDefault())
                messages.add(message)
            }
        }
        return messages
    }

    private fun getMessageText(mId: Int) : String? {
        val uri: Uri = Uri.parse("content://mms/part")
        val selection: String = "mid=$mId"
        val cursor : Cursor? = contentResolver.query(uri, null, selection, null, null)
        cursor?.use {
            if(it.moveToFirst()){
                val type = it.getString(it.getColumnIndex(Telephony.Mms.CONTENT_TYPE))
                if(type == "text/plain"){
                    return it.getString(it.getColumnIndex(Telephony.Mms.Part._DATA))
                }
                return "**$type**"
            }
        }
        return null
    }

    private fun getMessageAddress (mId: Int) : String {
        var address: String = ""
        val projection : Array<String> = arrayOf("address", "contact_id", "charset", "type")
        val uriBuilder : Uri.Builder = Telephony.Mms.CONTENT_URI.buildUpon()
        uriBuilder.appendPath(mId.toString()).appendPath("addr")

        val cursor : Cursor? = contentResolver.query(uriBuilder.build(), projection, null, null, null)
        cursor?.use {
            if (cursor.moveToFirst()){
                address = it.getString(it.getColumnIndex("address"))
            }
        }

        return address
    }
}