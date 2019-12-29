package io.daveyg.dev.convoexport

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.daveyg.dev.convoexport.conversation.Conversation
import io.daveyg.dev.convoexport.conversation.ConversationAggregator
import java.util.stream.Collector
import java.util.stream.Collectors


class MainActivity : AppCompatActivity() {
    val REQUEST_CODE_WRITE_TO_FILE : Int = 1
    val REQUEST_CODE_ASK_PERMISSIONS : Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val permissionsNotGranted : Array<String> = permissionsNotGranted()
        if(permissionsNotGranted.isNotEmpty()){
            ActivityCompat.requestPermissions(this, permissionsNotGranted, REQUEST_CODE_ASK_PERMISSIONS)
        }

        loadConversationsIntoListView()
    }

    private fun permissionsNotGranted() : Array<String>{
        val permissionsToCheck = mutableListOf(
            "android.permission.READ_SMS",
            "android.permission.READ_CONTACTS",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
        )

        return permissionsToCheck.stream()
            .filter{ ContextCompat.checkSelfPermission(baseContext, it) != PackageManager.PERMISSION_GRANTED }
            .toArray<String> { length -> arrayOfNulls(length)}

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
