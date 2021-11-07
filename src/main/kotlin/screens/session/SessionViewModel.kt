package screens.session

import data.repository.databaseChecker
import kotlinx.coroutines.launch
import utils.ViewModel
import java.security.Key

class SessionViewModel : ViewModel() {
    fun checkPassword(password: Key, callback: (result: Boolean) -> Unit) {
        viewModelScope.launch {
            callback(databaseChecker(password))
        }
    }
}