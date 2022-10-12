package loli.ball.mail

import emu.grasscutter.game.mail.Mail
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.days

@Serializable
data class Result(
    val code: Int,
    val msg: String
)

@OptIn(ExperimentalTime::class)
@Serializable
data class MailData constructor(
    val uid: Int,
    val title: String = "",
    val sender: String = "Server",
    val expireTime: Long = 30.days.inWholeSeconds,
    val importance: Int = 0,
    val body: MailBody
) {
    fun toMail() = Mail().also {
        it.mailContent.content = body.content
        it.mailContent.title = title
        it.mailContent.sender = sender
        it.importance = importance
        it.expireTime = expireTime + System.currentTimeMillis() / 1000
        it.itemList = body.items.map {
            Mail.MailItem(it.id, it.count, it.level)
        }
    }
}

@Serializable
data class MailBody(
    val content: String = "",
    val items: List<ItemInfo> = emptyList()
)

@Serializable
data class ItemInfo(
    var id: Int = 0,
    var count: Int = 0,
    var level: Int = 0
)
