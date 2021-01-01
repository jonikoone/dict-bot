package com.jonikoone.dictBot

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.jonikoone.dictBot.service.DictService
import com.jonikoone.dictBot.service.UserService
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction


fun main() {

    val t = System.getenv("token")

    Database.connect("jdbc:sqlite:test.s3db", driver = "org.sqlite.JDBC")

    transaction {
        StartingNote.insert {
            it[date] = System.currentTimeMillis()
        }
    }

    val bot = bot {

        DI.dependencies + ("bot" to this)

        token = t

        dispatch {
            command("start") {
                this.message.chat.username?.let { UserService.regUser(this.message.chat.id, it) }
            }

            command("createDict") {
                DictService.create(this.message.chat.id, args)
            }

            command("delDict") {
                var number = this.message.text ?: "non"
                bot.sendMessage(this.message.chat.id, "delete $number")
            }

            command("showMyDicts") {
                DictService.showDicts(this.message.chat.id)
            }

            command("createWord") {

            }
        }

    }

    bot.startPolling()
}


object DI {
    val dependencies = mutableMapOf<String, Any>()


}

object StartingNote: IntIdTable() {
    var date = long("start_time")
}
