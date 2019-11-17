package io.daveyg.dev.convoexport.conversation

import java.time.LocalDateTime

class SmsMessage(override val threadId: Int) : IMessage {

    override var date: LocalDateTime? = null

    override var address: String = ""

    override var subject: String? = null

    override var body: String? = null
        get() = field ?: "none"


}