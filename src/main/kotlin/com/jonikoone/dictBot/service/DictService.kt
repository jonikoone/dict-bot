package com.jonikoone.dictBot.service

import com.github.kotlintelegrambot.Bot
import com.jonikoone.dictBot.DI
import com.jonikoone.dictBot.dao.Dict
import com.jonikoone.dictBot.dao.Dicts
import org.jetbrains.exposed.sql.transactions.transaction

object DictService {

    private val bot: Bot by lazy { DI.dependencies["bot"] as Bot }

    fun create(chatId: Long, args: List<String>) {
        args.takeIf { it.size != 0 }?.first().let { dictName ->
            val user = UserService.getUserByChatId(chatId).first()
            transaction {

                var d = Dict.new {
                    this.nameDict = nameDict
                    this.creator = user
                }
            }
        }
    }

    fun showDicts(chatId: Long) {
        val user = UserService.getUserByChatId(chatId).first()

        val list = Dict.find {
            Dicts.creator eq user.id
        }.map {
            "${it.id}: ${it.nameDict}/n"
        }

        bot.sendMessage(chatId, "list of your dictionaries: $list")
    }

}
