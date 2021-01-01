package com.jonikoone.dictBot.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Words : IntIdTable() {
    val orig = varchar("original_word", 30)
    val translate = varchar("translate_word", 30)
    val useCase = varchar("use_case", 120)
}

class Word(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Word>(Words)

    val orig by Words.orig
    val translate by Words.translate
    val useCase by Words.useCase
}
