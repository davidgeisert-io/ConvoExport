package io.daveyg.dev.convoexport.conversation

import java.time.LocalDateTime

interface IMessage {

    val threadId: Int
    var address : String
    var name : String?
    var subject: String?
    var body: String?
    var date: LocalDateTime?

}