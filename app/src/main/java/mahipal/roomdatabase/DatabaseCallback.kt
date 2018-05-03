package mahipal.roomdatabase

interface DatabaseCallback {

    fun dataAdded()
    fun dataUpdated()
    fun dataRemoved()
    fun getUserDataById(user: User)
    fun dataNotAvailable(error: String)
}