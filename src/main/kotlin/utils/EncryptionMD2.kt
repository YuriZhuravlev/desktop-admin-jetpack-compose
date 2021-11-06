package utils

import data.db.entity.DBUser
import data.db.entity.UserTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

private const val NAME_DB = "data.db"
private const val NAME_BIN = "data.bin"

fun databaseChecker(key: String): Boolean {
    val file = File(NAME_BIN)
    if (!file.exists()) {
        // если файл не существует, то key - кодовая фраза для будущих данных
        return true
    }
    decrypt(key)
    var res = false
    try {
        val db = Database.connect("jdbc:sqlite:${NAME_DB}", "org.sqlite.JDBC")

        transaction(db) {
            addLogger(StdOutSqlLogger)
            if (SchemaUtils.checkCycle(UserTable) && DBUser.findById(0)!!.username == "ADMIN") {
                res = true
            }
        }
    } catch (e: Exception) {
        println(e.stackTrace)
    }
    return res
}

fun encrypt(key: String) {
    // TODO("")
}

fun decrypt(key: String) {
    // TODO("")
}