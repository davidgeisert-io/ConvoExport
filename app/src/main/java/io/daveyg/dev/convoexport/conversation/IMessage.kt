package io.daveyg.dev.convoexport.conversation

interface IMessage {

    val threadId: Int
    var address : String
    var subject: String?
    var body: String?

}