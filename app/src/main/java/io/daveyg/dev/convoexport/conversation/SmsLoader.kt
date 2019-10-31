package io.daveyg.dev.convoexport.conversation

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri

class SmsLoader(var contentResolver: ContentResolver) : IMessageLoader {

    override fun loadMessages(): List<IMessage> {
        val messages: ArrayList<IMessage> = ArrayList<IMessage>()
        val cursor: Cursor? = contentResolver.query(Uri.parse("content://sms/"), null, null, null, null, null)
        cursor?.use {
            if (cursor.moveToFirst()) {
                val threadIdIndex : Int = cursor.getColumnIndex("thread_id")
                val addressIndex : Int = cursor.getColumnIndex("address")
                val subjectIndex : Int = cursor.getColumnIndex("subject")
                val bodyIndex: Int = cursor.getColumnIndex("body")

                do {
                    val message : SmsMessage = SmsMessage(cursor.getInt(threadIdIndex))
                    message.address = cursor.getString(addressIndex)
                    message.subject = cursor.getString(subjectIndex)
                    message.body = cursor.getString(bodyIndex)

                    messages.add(message)
                } while (cursor.moveToNext())
            }

        }
        return messages
    }
}