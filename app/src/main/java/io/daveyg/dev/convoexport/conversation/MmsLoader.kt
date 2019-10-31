package io.daveyg.dev.convoexport.conversation

import android.content.ContentResolver

class MmsLoader(contextResolver: ContentResolver) : IMessageLoader {

    override fun loadMessages(): List<IMessage> {
        throw NotImplementedError()
    }
}