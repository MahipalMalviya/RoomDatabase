package mahipal.roomdatabase

import android.arch.persistence.room.*

@Dao
interface UserDao {

    @Insert
    fun insertUserData(userData : User)

    @Update
    fun updateUserData(userData: User)

    @Delete
    fun deleteUserData(userData: User)

    @Query("SELECT * FROM User WHERE userId = :userId")
    fun getDataByUserId(userId: Int): User
}