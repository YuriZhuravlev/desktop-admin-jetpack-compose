package screens.main

import data.model.UIUser
import data.repository.RepositoryUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import utils.ViewModel

class MainViewModel(private val repositoryUser: RepositoryUser) : ViewModel() {
    private val _profile = MutableStateFlow<UIUser?>(null)
    val profile = _profile.asStateFlow()

    fun login(user: UIUser) {
        viewModelScope.launch {
            _profile.emit(user)
        }
    }

    fun logout(user: UIUser) {
        viewModelScope.launch {
            _profile.emit(null)
        }
    }
}