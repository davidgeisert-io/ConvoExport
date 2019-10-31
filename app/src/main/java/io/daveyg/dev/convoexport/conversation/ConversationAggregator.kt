package io.daveyg.dev.convoexport.conversation

import android.content.ContentResolver

class ConversationAggregator(contextResolver: ContentResolver) {
    val smsLoader: IMessageLoader
    val mmsLoader: IMessageLoader

    init {
        smsLoader = SmsLoader(contextResolver)
        mmsLoader = MmsLoader(contextResolver)
    }


    fun AggregateAll(): List<Conversation>{
        val smsMessages : List<IMessage> = smsLoader.loadMessages()
        val mmsMessages : List<IMessage> = mmsLoader.loadMessages()

        return emptyList()
    }
}