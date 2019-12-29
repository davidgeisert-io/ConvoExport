package io.daveyg.dev.convoexport.conversation

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract

class ContactAddressLookup {

    companion object {
        fun getContactNameFromNumber (contentResolver: ContentResolver, number: String) : String {
            val uri: Uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number))
            val projection: Array<String> = arrayOf(ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID)
            val cursor: Cursor? = contentResolver.query(uri, projection, null, null, null)
            cursor?.use {
                if (it.moveToFirst()){
                    return it.getString(it.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME))
                }
            }
            return ""
        }
    }
}