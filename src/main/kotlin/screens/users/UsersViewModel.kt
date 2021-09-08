package screens.users

import data.Resource
import data.model.UIUser
import data.repository.RepositoryUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import utils.ViewModel

class UsersViewModel(private val repositoryUser: RepositoryUser) : ViewModel() {
    private val _users = MutableStateFlow<Resource<List<UIUser>>>(Resource.loading())
    val users = _users.asStateFlow()

    fun getUsers() {
        viewModelScope.launch {
            _users.emit(repositoryUser.getUsers())
        }
    }
}