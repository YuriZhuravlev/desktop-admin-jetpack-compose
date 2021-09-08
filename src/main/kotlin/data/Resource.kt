package data

/**
 * Класс ресурсов для получения данных и информации о процессе загрузки или произошедшей ошибке.
 *
 * [T] - Тип данных, которые необходимо получить.
 *
 * @param status - Текущее состояние ресурса.
 * @param data - Данные ресурса.
 * @param error - Ошибка, возникшая при обращении к ресурсу.
 */
data class Resource<T>(
    val status: Status,
    val data: T? = null,
    val error: Throwable? = null
) {

    /**
     * Метод для выполнения части [block] если текущее состояние равно [ERROR].
     *
     * @param block - блок для выполнения.
     * @return текущий ресурс.
     */
    fun ifError(block: (error: Throwable) -> Unit): Resource<T> {
        if (status.isError()) {
            block(error!!)
        }
        return this
    }

    /**
     * Метод для выполнения части [block] если текущее состояние равно [SUCCESS].
     *
     * @param block - блок для выполнения.
     * @return текущий ресурс.
     */
    fun ifSuccess(block: (data: T?) -> Unit): Resource<T> {
        if (status.isSuccess()) {
            block(data)
        }
        return this
    }

    /**
     * Метод для выполнения части [block] если текущее состояние равно [LOADING].
     *
     * @param block - блок для выполнения.
     * @return текущий ресурс.
     */
    fun ifLoading(block: (data: T?) -> Unit): Resource<T> {
        if (status.isLoading()) {
            block(data)
        }
        return this
    }

    /**
     * Метод для выполнения части [block] если текущее состояние равно [SUCCESS] или [ERROR].
     *
     * @param block - блок для выполнения.
     * @return текущий ресурс.
     */
    fun ifDone(block: () -> Unit): Resource<T> {
        if (status.isDone()) {
            block()
        }
        return this
    }

    companion object {

        /**
         * Создание ресурса с готовыми данными.
         */
        fun <T> success(data: T): Resource<T> =
            Resource(
                Status.SUCCESS,
                data,
                null
            )

        /**
         * Создание ресурса с ошибкой.
         */
        fun <T> error(error: Throwable, data: T? = null): Resource<T> =
            Resource(
                Status.ERROR,
                data,
                error
            )

        /**
         * Создание ресурса в состоянии загрузки.
         */
        fun <T> loading(data: T? = null): Resource<T> =
            Resource(
                Status.LOADING,
                data,
                null
            )
    }

    /**
     * Возможные состояния ресурса.
     */
    enum class Status {

        /**
         * Данные получены.
         */
        SUCCESS,

        /**
         * Произошла ошибка при получении данных.
         */
        ERROR,

        /**
         * Идет процесс получения данных.
         */
        LOADING;

        /**
         * Проверка на состояние [SUCCESS].
         *
         * @return true если текущее состояние равно [SUCCESS], в противном случае false.
         */
        fun isSuccess(): Boolean = this == SUCCESS

        /**
         * Проверка на состояние [ERROR].
         *
         * @return true если текущее состояние равно [ERROR], в противном случае false.
         */
        fun isError(): Boolean = this == ERROR

        /**
         * Проверка на состояние [LOADING].
         *
         * @return true если текущее состояние равно [LOADING], в противном случае false.
         */
        fun isLoading(): Boolean = this == LOADING

        /**
         * Проверка на состояние на завершенный статус.
         *
         * Большей действий над ресурсом для получения данных не подразумевается.
         *
         * @return true если текущее состояние равно [SUCCESS] или [ERROR], в противном случае false.
         */
        fun isDone(): Boolean = (this == SUCCESS || this == ERROR)
    }
}
