package io.daveyg.dev.convoexport

import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    val REQUEST_CODE_WRITE_TO_FILE : Int = 1
    val REQUEST_CODE_ASK_PERMISSIONS : Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(ContextCompat.checkSelfPermission(baseContext, "android.permission.READ_SMS") != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf("android.permission.READ_SMS"), REQUEST_CODE_ASK_PERMISSIONS)
        }

        val button : Button = findViewById(R.id.create_file_button);
        button.setOnClickListener { v -> createDocument("text/csv", "AllTextMessages")}

    }

    private fun createDocument(mimeType: String, fileName : String){
        var intent: Intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.setType(mimeType)
        intent.putExtra(Intent.EXTRA_TITLE, fileName)
        startActivityForResult(intent, REQUEST_CODE_WRITE_TO_FILE)
    }


    fun writeMessagesToFile( uri: Uri){
        val cursor: Cursor? = contentResolver.query(Uri.parse("content://sms/"), null, null, null, null, null)
        cursor?.use {
            contentResolver.openOutputStream(uri)?.use {
                stream ->
                val columnNames : List<String> = cursor.columnNames
                    ?.map { n -> String.format("\"%s\"", n) }
                    ?: emptyList()

                val header : String = columnNames.joinToString(",")
                stream.write(header.toByteArray())
                stream.write("\n".toByteArray())

                if(cursor.moveToFirst()){
                    do {
                        val recordList : ArrayList<String> = ArrayList()
                        for(name: String in cursor.columnNames){
                            val idx : Int = cursor.getColumnIndex(name);
                            val value : String = String.format("\"%s\"", cursor.getString(idx) ?: "")
                            recordList.add(value)

                        }
                        val record: String = recordList.joinToString(",")
                        stream.write(record.toByteArray())
                        stream.write("\n".toByteArray())
                    } while (cursor.moveToNext())
                }

            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_WRITE_TO_FILE && data != null){
            writeMessagesToFile(data.data ?: Uri.EMPTY)
            Toast.makeText(applicationContext, data.data?.toString(), Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(applicationContext, "No Data Provided", Toast.LENGTH_LONG).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
