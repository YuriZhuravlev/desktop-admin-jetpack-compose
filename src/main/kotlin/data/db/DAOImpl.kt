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
        val file = File("data/data.db")
        if (!file.exists()) {
            file.createNewFile()
            useResource("data/data.db") {
                it.copyTo(file.outputStream())
            }
        }
        Database.connect("jdbc:sqlite:file:data/data.db", "org.sqlite.JDBC")
    }

    override fun getUserByName(name: String): DBUser? {
        var user: DBUser? = null
        transaction(db) {
            addLogger(StdOutSqlLogger)
            user = DBUser.find { UserTable.username eq name }.firstOrNull()
        }
        return user
    }

    override fun getUsers(): List<DBUser> {
        val list = mutableListOf<DBUser>()
        transaction(db) {
            addLogger(StdOutSqlLogger)
            list.addAll(DBUser.all())
        }
        return list
    }

    override fun patchUser(user: DBUser) {
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