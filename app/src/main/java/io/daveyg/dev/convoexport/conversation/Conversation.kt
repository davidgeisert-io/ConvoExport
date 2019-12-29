package io.daveyg.dev.convoexport.conversation

import java.time.LocalDateTime

class Conversation(val threadId: Int) {
    var messages: List<IMessage>? = emptyList()
    var lastActivity : LocalDateTime ? = null
    var displayTitle : String? = ""

}