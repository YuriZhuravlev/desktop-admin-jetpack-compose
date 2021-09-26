package data.db

import data.db.entity.DBUser

interface DAO {
    suspend fun getUser(id: Int): DBUser?
    suspend fun getUserByName(name: String): DBUser?
    suspend fun getUsers(): List<DBUser>
    suspend fun patchUser(user: DBUser)
    suspend fun editPassword(userId: Int, newPassword: ByteArray): DBUser?
    suspend fun editIsBlocked(id: Int, fl: Boolean): DBUser?
    suspend fun editStrongPassword(id: Int, fl: Boolean): DBUser?
    suspend fun addUser(username: String, strongPassword: Boolean): DBUser
}