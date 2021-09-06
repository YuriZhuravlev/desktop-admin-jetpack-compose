package data.db

import data.db.entity.DBUser

interface DAO {
    fun getUserByName(name: String): DBUser?
    fun getUsers(): List<DBUser>
    fun patchUser(user: DBUser)
}