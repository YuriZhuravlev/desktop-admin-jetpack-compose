package screens.session

import data.model.SessionState
import utils.ViewModel

class SessionViewModel : ViewModel() {
    fun checkPassword(password: String): SessionState {
        return SessionState.CLOSE_SESSION
    }
}