package data.db

import androidx.compose.ui.res.useResource
import data.db.entity.DBUser
import data.db.entity.UserTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File


object DAOImpl : DAO {
    private val db by lazy {
        val file = File("data.db")
        if (!file.exists()) {
            file.createNewFile()
            useResource("data.db") {
                it.copyTo(file.outputStream())
            }
        }
        Database.connect("jdbc:sqlite:data.db", "org.sqlite.JDBC")
    }

    override suspend fun getUser(id: Int): DBUser? {
        return transaction(db) {
            addLogger(StdOutSqlLogger)
            DBUser.findById(id)
        }
    }

    override suspend fun getUserByName(name: String): DBUser? {
        return transaction(db) {
            addLogger(StdOutSqlLogger)
            DBUser.find { UserTable.username eq name }.firstOrNull()
        }
    }

    override suspend fun getUsers(): List<DBUser> {
        return transaction(db) {
            addLogger(StdOutSqlLogger)
            DBUser.all().toList()
        }
    }

    override suspend fun patchUser(user: DBUser) {
        transaction(db) {
            addLogger(StdOutSqlLogger)
            DBUser[user.id].apply {
                username = user.username
                password = user.password
                isBlocked = user.isBlocked
                strongPassword = user.strongPassword
            }
        }
    }


}