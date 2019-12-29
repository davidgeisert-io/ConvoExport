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


    fun aggregateAll(): List<Conversation> {
        val allMessages = mutableListOf<IMessage>()
        allMessages.addAll(smsLoader.loadMessages())
        allMessages.addAll(mmsLoader.loadMessages())

        allMessages.sortByDescending {
            it.date
        }

        val threadGroup: Map<Int, List<IMessage>> = allMessages.stream()
            .collect(Collectors.groupingBy(IMessage::threadId))

        val allConversations = mutableListOf<Conversation>()
        for (k in threadGroup.keys) {
            val messages = threadGroup.getOrDefault(k, emptyList())
            val c: Conversation = Conversation(k)
            c.messages = messages
            c.lastActivity = messages.maxBy { it.threadId }?.date
            c.displayTitle = getDisplayTitleFromMessages(messages)

            allConversations.add(c)
        }
        return allConversations
    }

    private fun getDisplayTitleFromMessages(messages: List<IMessage>) : String {
        return messages.stream().map { it.name }.distinct().collect(Collectors.joining(", "))
    }
}