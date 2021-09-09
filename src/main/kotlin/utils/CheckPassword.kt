package utils


fun checkPassword(newPassword: String): Boolean {
    return (newPassword.contains(Regex("[a-zA-Z]"))
            && newPassword.contains(Regex("[а-яА-ЯёЁ]")))
}