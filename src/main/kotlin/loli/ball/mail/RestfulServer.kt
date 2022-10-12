package loli.ball.mail

import emu.grasscutter.Grasscutter
import emu.grasscutter.database.DatabaseHelper
import emu.grasscutter.game.player.Player
import fi.iki.elonen.NanoHTTPD
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun main(args: Array<String>) {
    RestfulServer().start()
}

class RestfulServer(host: String = "127.0.0.1", port: Int = 49516) : NanoHTTPD(host, port) {

    override fun start(timeout: Int) {
        super.start(timeout, false)
    }

    override fun serve(pSession: IHTTPSession?): Response {
        val json = pSession?.let { session ->
            mutableMapOf<String, String>().let {
                session.parseBody(it)
                it["postData"]
            }
        }
        return newFixedLengthResponse(
            if (json == null) {
                Result(400, "no data").toJson()
            } else {
                runCatching {
                    handle(json)
                }.getOrElse {
                    it.printStackTrace()
                    Result(500, "server error").toJson()
                }
            }
        )
    }

    private fun handle(json: String): String {
        sendMail(json.fromJson())
        return Result(0, "success").toJson()
    }

    private fun sendMail(mail: MailData) {
        val mail1 = mail.toMail()
        val player: Player? = Grasscutter.getGameServer().getPlayerByUid(mail.uid)
        if (player != null) {
            player.sendMail(mail1)
        } else {
            mail1.ownerUid = mail.uid
            DatabaseHelper.saveMail(mail1)
        }
    }

}

inline fun <reified T : Any> T.toJson() = Json.encodeToString(this)

inline fun <reified T> String.fromJson() = Json.decodeFromString<T>(this)