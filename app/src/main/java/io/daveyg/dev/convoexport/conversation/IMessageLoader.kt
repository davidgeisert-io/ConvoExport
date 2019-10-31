package io.daveyg.dev.convoexport.conversation

interface IMessageLoader {
    fun loadMessages() : List<IMessage>
}