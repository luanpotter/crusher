package xyz.luan.crusher.model

import org.jetbrains.exposed.dao.*

object Crons : IntIdTable() {
    val name = varchar("name", length = 255).index()
    val userEmail = varchar("user_email", length = 255).index()

    val cronString = varchar("cron_string", length = 255)
    val pushTitle = text("push_title")
    val pushText = text("push_text")
}

class Cron(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Cron>(Crons)

    var name by Crons.name
    var userEmail by Crons.userEmail
    var cronString by Crons.cronString
    var pushTitle by Crons.pushTitle
    var pushText by Crons.pushText
}