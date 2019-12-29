package io.daveyg.dev.convoexport.conversation

import java.time.LocalDateTime

class MmsMessage(override val threadId: Int) : IMessage {

    override var address: String = ""
    override var name: String? = ""
    override var subject: String? = null

    override var body: String? = null
        get() = field ?: "none"

    override var date: LocalDateTime? = null
}