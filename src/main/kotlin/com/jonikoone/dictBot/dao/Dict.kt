package com.jonikoone.dictBot.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Dicts : IntIdTable() {
    val nameDict = varchar("name_dict", 50)
    val creator = reference("creator", Users)
}

class Dict(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Dict>(Dicts)
    var nameDict by Dicts.nameDict
    var creator by User referencedOn Dicts.creator
}

