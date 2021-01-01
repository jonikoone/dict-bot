package com.jonikoone.dictBot.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Users : IntIdTable() {
    val chatId = long("chat_id").index()
    val nickname = varchar("nickname", 255)
}

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var chatId by Users.chatId
    var nickname by Users.nickname
    var dict by Dict referencedOn Users.id
}
