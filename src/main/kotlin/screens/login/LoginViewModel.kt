package screens.login

import data.Resource
import data.model.UIUser
import data.repository.RepositoryUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import utils.ViewModel

class LoginViewModel(private val repositoryUser: RepositoryUser) : ViewModel() {
    private val _user = MutableStateFlow<Resource<UIUser?>?>(null)
    val user = _user.asStateFlow()

    fun getUser(name: String) {
        viewModelScope.launch {
            _user.emit(Resource.loading())
            _user.emit(repositoryUser.getUserByName(name))
        }
    }

    fun checkPassword(password: String) {
        TODO("Not yet implemented")
    }
}