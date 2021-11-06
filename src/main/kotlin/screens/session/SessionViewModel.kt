package screens.session

import kotlinx.coroutines.launch
import utils.ViewModel
import utils.databaseChecker

class SessionViewModel : ViewModel() {
    fun checkPassword(password: String, callback: (result: Boolean) -> Unit) {
        viewModelScope.launch {
            callback(databaseChecker(password))
        }
    }
}