package mahipal.roomdatabase

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE

@Dao
interface UserDao {

    @Insert
    fun insertUserData(userData : User)

    @Update(onConflict = REPLACE)
    fun updateUserData(userData: User)

    @Query("DELETE FROM User WHERE userId = :userId")
    fun deleteUserData(userId: Int)

    @Query("SELECT * FROM User WHERE userId = :userId")
    fun getDataByUserId(userId: Int): User
}