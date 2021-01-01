package com.jonikoone.dictBot.service

import com.github.kotlintelegrambot.Bot
import com.jonikoone.dictBot.DI
import com.jonikoone.dictBot.dao.User
import com.jonikoone.dictBot.dao.Users
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object UserService {

    val bot: Bot by lazy {
        DI.dependencies["bot"] as Bot
    }

    fun regUser(chatId: Long, nickname: String) {

        checkUser(chatId)?.let {
            bot.sendMessage(chatId, "С возвращением, $it")
            return
        }

        transaction {
            val user = User.new {
                this.chatId = chatId
                this.nickname = nickname
            }
        }

    }

    private fun checkUser(chatId: Long): String? {
        var user: String? = null
        transaction {
            user = Users.select {
                Users.chatId eq chatId
            } .first()[Users.nickname]
        }

        return user;
    }

    fun getUserByChatId(chatId: Long) = transaction {
        return@transaction User.find {
            Users.chatId eq chatId
        }
    }

}

