package io.daveyg.dev.convoexport

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.daveyg.dev.convoexport.conversation.Conversation
import io.daveyg.dev.convoexport.conversation.ConversationAggregator


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
        val conversations : List<Conversation> = conversationAggregator.aggregateAll()
        //TODO: Load conversations Into ListView
    }

    private fun writeConversationsToFile(){
        //TODO: Implement Me

    }


}
