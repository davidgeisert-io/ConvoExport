package io.daveyg.dev.convoexport.conversation

import android.content.ContentResolver
import java.util.stream.Collectors

class ConversationAggregator(contentResolver: ContentResolver) {
    val smsLoader: IMessageLoader
    val mmsLoader: IMessageLoader

    init {
        smsLoader = SmsLoader(contentResolver)
        mmsLoader = MmsLoader(contentResolver)
    }


    fun AggregateAll(): List<Conversation>{
        val smsMessages : List<IMessage> = smsLoader.loadMessages()
        val mmsMessages : List<IMessage> = mmsLoader.loadMessages()

        //TODO: Sort-by date
        val smsThreadGroup : Map<Int, List<IMessage>> = smsMessages.stream()
            .collect(Collectors.groupingBy(IMessage::threadId))

        return smsThreadGroup.keys.stream()
            .map { id ->
                val c : Conversation = Conversation(id)
                c.messages = smsThreadGroup.getOrDefault(id, emptyList())
                c
            }
            .collect(Collectors.toList())
    }
}