package data.db.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object UserTable : IntIdTable("user_table") {
    val username: Column<String> = varchar("username", 50)
    val password: Column<String> = varchar("password", 50).uniqueIndex()
    val isBlocked: Column<Boolean> = bool("is_blocked")
    val strongPassword: Column<Boolean> = bool("strong_password")
}

class DBUser(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<DBUser>(UserTable)

    var username by UserTable.username
    var password by UserTable.password
    var isBlocked by UserTable.isBlocked
    var strongPassword by UserTable.strongPassword
}