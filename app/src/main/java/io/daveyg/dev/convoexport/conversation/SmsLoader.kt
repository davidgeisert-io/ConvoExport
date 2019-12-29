package io.daveyg.dev.convoexport.conversation

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.Telephony
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class SmsLoader(var contentResolver: ContentResolver) : IMessageLoader {

    override fun loadMessages(): List<IMessage> {
        val messages: ArrayList<IMessage> = ArrayList<IMessage>()
        val cursor: Cursor? = contentResolver.query(Telephony.Sms.CONTENT_URI, null, null, null, null, null)
        cursor?.use {
            if (cursor.moveToFirst()) {
                val threadIdIndex : Int = cursor.getColumnIndex(Telephony.Sms.THREAD_ID)
                val addressIndex : Int = cursor.getColumnIndex(Telephony.Sms.ADDRESS)
                val subjectIndex : Int = cursor.getColumnIndex(Telephony.Sms.SUBJECT)
                val bodyIndex: Int = cursor.getColumnIndex(Telephony.Sms.BODY)
                val dateIndex: Int = cursor.getColumnIndex(Telephony.Sms.DATE)

                do {
                    val message : SmsMessage = SmsMessage(cursor.getInt(threadIdIndex))
                    message.address = cursor.getString(addressIndex)
                    message.name = ContactAddressLookup.getContactNameFromNumber(contentResolver, message.address)
                    message.subject = cursor.getString(subjectIndex)
                    message.body = cursor.getString(bodyIndex)
                    message.date = LocalDateTime.ofInstant(Instant.ofEpochMilli(cursor.getLong(dateIndex)), ZoneOffset.systemDefault())
                    messages.add(message)
                } while (cursor.moveToNext())
            }

        }
        return messages
    }
}