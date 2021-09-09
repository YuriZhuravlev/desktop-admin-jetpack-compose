package screens.users

import data.Resource
import data.model.UIUser
import data.repository.RepositoryUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import utils.ViewModel

class UsersViewModel(private val repositoryUser: RepositoryUser) : ViewModel() {
    private val _users = MutableStateFlow<Resource<List<UIUser>>>(Resource.loading())
    val users = _users.asStateFlow()

    private val _selectedUser = MutableStateFlow<Resource<UIUser?>>(Resource.success(null))
    val selectedUser = _selectedUser.asStateFlow()

    fun getUsers() {
        viewModelScope.launch {
            _users.emit(repositoryUser.getUsers())
        }
    }

    fun patchIsBlocked(fl: Boolean) {
        viewModelScope.launch {
            _selectedUser.value.data?.let {
                _selectedUser.emit(repositoryUser.editIsBlocked(it.id, fl))
                launch {
                    delay(100)
                    getUsers()
                }
            }
        }
    }

    fun patchStrongPassword(fl: Boolean) {
        viewModelScope.launch {
            _selectedUser.emit(Resource.loading(_selectedUser.value.data))
            _selectedUser.value.data?.let {
                _selectedUser.emit(repositoryUser.editStrongPassword(it.id, fl))
                launch {
                    delay(100)
                    getUsers()
                }
            }
        }
    }

    fun selectUser(uiUser: UIUser?) {
        viewModelScope.launch {
            _selectedUser.emit(Resource.success(uiUser))
        }
    }

    fun backUser() {
        viewModelScope.launch {
            _selectedUser.emit(Resource.loading(_selectedUser.value.data))
            _selectedUser.value.let { oldUser ->
                val list = users.value.data
                list?.indexOfFirst {
                    it.id == oldUser.data?.id
                }?.let { index ->
                    when {
                        (index == -1 || index == 0) -> selectUser(list.lastOrNull())
                        else -> selectUser(list[index - 1])
                    }
                }
            }
        }
    }

    fun nextUser() {
        viewModelScope.launch {
            _selectedUser.value.data?.let { oldUser ->
                val list = users.value.data
                list?.indexOfFirst {
                    it.id == oldUser.id
                }?.let { index ->
                    when {
                        (index == -1 || index == list.lastIndex) -> selectUser(list.firstOrNull())
                        else -> selectUser(list[index + 1])
                    }
                }
            }
        }
    }
}