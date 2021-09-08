package data.db

import data.db.entity.DBUser

interface DAO {
    suspend fun getUser(id: Int): DBUser?
    suspend fun getUserByName(name: String): DBUser?
    suspend fun getUsers(): List<DBUser>
    suspend fun patchUser(user: DBUser)
    suspend fun editPassword(userId: Int, newPassword: String)
}