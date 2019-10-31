package io.daveyg.dev.convoexport.conversation

class SmsMessage(override val threadId: Int) : IMessage {

    override var address: String = ""

    override var subject: String? = null

    override var body: String? = null
        get() = field ?: "none"


}