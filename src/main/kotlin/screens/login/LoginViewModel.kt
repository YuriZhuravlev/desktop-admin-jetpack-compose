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

    private val _auth = MutableStateFlow<Resource<UIUser>>(Resource.loading())
    val auth = _auth.asStateFlow()

    fun getUser(name: String) {
        viewModelScope.launch {
            _user.emit(Resource.loading())
            _user.emit(repositoryUser.getUserByName(name))
        }
    }

    fun checkPassword(password: String) {
        viewModelScope.launch {
            val profile = _user.value
            if (profile?.data?.password == password)
                profile.data.let {
                    _auth.emit(Resource(profile.status, it, profile.error))
                }
            else
                _auth.emit(Resource.error(InvalidPassword()))
        }
    }

    fun newPassword() {
        viewModelScope.launch {
            val profile = _user.value
            profile?.data.let {
                _auth.emit(Resource(Resource.Status.SUCCESS, it, profile?.error))
            }
        }
    }

    class InvalidPassword : Throwable()
}