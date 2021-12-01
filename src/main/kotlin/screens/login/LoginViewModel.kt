package screens.login

import data.Resource
import data.model.UIUser
import data.repository.RepositoryUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import utils.SignatureVerification.signatureVerification
import utils.ViewModel

class LoginViewModel(private val repositoryUser: RepositoryUser) : ViewModel() {
    private val _user = MutableStateFlow<Resource<UIUser?>?>(null)
    val user = _user.asStateFlow()

    private val _auth = MutableStateFlow<Resource<UIUser>>(Resource.loading())
    val auth = _auth.asStateFlow()

    private val _verification = MutableStateFlow<Resource<Boolean>>(Resource.loading())
    val verification = _verification.asStateFlow()

    init {
        // проверка не требует вмешательства пользователя, поэтому запускается автоматически
        verify()
    }

    /**
     * Проверка ЭП
     */
    private fun verify() {
        viewModelScope.launch {
            _verification.emit(Resource.success(signatureVerification()))
        }
    }

    fun getUser(name: String) {
        viewModelScope.launch {
            _user.emit(Resource.loading())
            val res = repositoryUser.getUserByName(name)
            _user.emit(
                if (res.data?.isBlocked == true)
                    Resource.error(BlockedUser())
                else
                    res
            )
        }
    }

    fun checkPassword(inputPassword: String) {
        viewModelScope.launch {
            val profile = _user.value
            val password = profile?.data?.let {
                repositoryUser.decryptionPassword(it)
            }
            if (password == inputPassword)
                profile.data.let {
                    _auth.emit(Resource(profile.status, it, profile.error))
                }
            else
                _auth.emit(Resource.error(InvalidPassword()))
        }
    }

    fun newPassword(user: UIUser) {
        viewModelScope.launch {
            _auth.emit(Resource.success(user))
        }
    }

    class InvalidPassword : Throwable()
    class BlockedUser : Throwable()
}