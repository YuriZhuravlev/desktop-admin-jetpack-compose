package screens.change_password

import data.model.UIUser
import data.repository.RepositoryUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import utils.ViewModel

class ChangePasswordViewModel(private val userId: Int, private val repositoryUser: RepositoryUser) : ViewModel() {
    private val _result = MutableStateFlow<UIUser?>(null)
    val result = _result.asStateFlow()

    fun edit(newPassword: String) {
        viewModelScope.launch {
            _result.emit(repositoryUser.editPassword(userId, newPassword))
        }
    }
}
