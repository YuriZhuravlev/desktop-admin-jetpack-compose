package screens.change_password

import data.Resource
import data.model.UIUser
import data.repository.RepositoryUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import utils.ViewModel
import utils.checkPassword

class ChangePasswordViewModel(private val user: UIUser, private val repositoryUser: RepositoryUser) : ViewModel() {
    private val _result = MutableStateFlow<Resource<UIUser>?>(null)
    val result = _result.asStateFlow()
    val strongPassword get() = user.strongPassword

    fun edit(newPassword: String) {
        viewModelScope.launch {
            _result.emit(Resource.loading())
            // 8.	Наличие латинских букв и символов кириллицы.
            if (!strongPassword ||
                checkPassword(newPassword)
            ) {
                repositoryUser.editPassword(user.id, newPassword)?.let {
                    _result.emit(Resource.success(it))
                }
            } else {
                _result.emit(Resource.error(Throwable()))
            }
        }
    }
}
