package data.repository

import data.Resource
import data.db.DAO
import data.db.DAOImpl
import data.db.entity.DBUser
import data.model.UIUser

object RepositoryUser {
    private val dao: DAO = DAOImpl

    suspend fun getUserByName(name: String): Resource<UIUser?> {
        return try {
            Resource.success(dao.getUserByName(name)?.toUIUser())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(e)
        }
    }

    suspend fun getUsers(): Resource<List<DBUser>> {
        return try {
            Resource.success(dao.getUsers())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(e)
        }
    }

    suspend fun editPassword(userId: Int, newPassword: String) {
        dao.editPassword(userId, newPassword)
    }
}

fun DBUser.toUIUser() = UIUser(id.value, username, password, isBlocked, strongPassword)
