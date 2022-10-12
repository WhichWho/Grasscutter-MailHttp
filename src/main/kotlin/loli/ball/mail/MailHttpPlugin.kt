package loli.ball.mail

import emu.grasscutter.plugin.Plugin


class MailHttpPlugin : Plugin() {

    private lateinit var server: RestfulServer

    override fun onEnable() {
        server = RestfulServer()
        server.start();
        super.onEnable()
    }

    override fun onDisable() {
        server.stop()
        super.onDisable()
    }

}