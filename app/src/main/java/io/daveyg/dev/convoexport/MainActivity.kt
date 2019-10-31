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
import io.daveyg.dev.convoexport.conversation.Conversation
import io.daveyg.dev.convoexport.conversation.ConversationAggregator
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

        loadConversationsIntoListView()
    }


    private fun loadConversationsIntoListView(){
        val conversationAggregator : ConversationAggregator = ConversationAggregator(contentResolver)
        val conversations : List<Conversation> = conversationAggregator.AggregateAll()
        //TODO: Load conversations Into ListView
    }

    private fun writeConversationsToFile(){
        //TODO: Implement Me

    }


}
