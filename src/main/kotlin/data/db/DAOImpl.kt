package data.db

import androidx.compose.ui.res.useResource
import data.db.entity.DBUser
import data.db.entity.UserTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File


object DAOImpl : DAO {
    private val db by lazy {
        val file = File("data.d")
        if (!file.exists()) {
            file.createNewFile()
            useResource("data.d") {
                it.copyTo(file.outputStream())
            }
        }
        val db = Database.connect("jdbc:sqlite:data.d", "org.sqlite.JDBC")

        transaction(db) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(UserTable)
            if (DBUser.all().empty())
                DBUser.new {
                    this.username = "ADMIN"
                    password = ByteArray(0)
                    isBlocked = false
                    strongPassword = false
                }
        }
        db
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

    override suspend fun editPassword(userId: Int, newPassword: ByteArray): DBUser? {
        return transaction(db) {
            DBUser[userId]?.apply {
                password = newPassword
            }
        }
    }

    override suspend fun editIsBlocked(id: Int, fl: Boolean): DBUser? {
        return transaction(db) {
            DBUser[id]?.apply {
                isBlocked = fl
            }
        }
    }

    override suspend fun editStrongPassword(id: Int, fl: Boolean): DBUser? {
        return transaction(db) {
            DBUser[id]?.apply {
                strongPassword = fl
            }
        }
    }

    override suspend fun addUser(username: String, strongPassword: Boolean): DBUser {
        return transaction(db) {
            DBUser.new {
                this.username = username
                password = ByteArray(0)
                isBlocked = false
                this.strongPassword = strongPassword
            }
        }
    }
}