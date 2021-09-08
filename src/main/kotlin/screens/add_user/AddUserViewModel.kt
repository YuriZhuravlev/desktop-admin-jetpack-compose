package screens.add_user

import data.Resource
import data.model.UIUser
import data.repository.RepositoryUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import utils.ViewModel

class AddUserViewModel(private val repositoryUser: RepositoryUser) : ViewModel() {
    private val _result = MutableStateFlow<Resource<UIUser>>(Resource.loading())
    val result = _result.asStateFlow()

    fun addUser(name: String, strongPassword: Boolean) {
        viewModelScope.launch {
            _result.emit(repositoryUser.addUser(name, strongPassword))
        }
    }
}