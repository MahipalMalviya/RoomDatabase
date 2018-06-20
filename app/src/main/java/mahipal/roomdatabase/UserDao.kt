package mahipal.roomdatabase

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE

@Dao
interface UserDao {

    @Insert(onConflict = REPLACE)
    fun insertUserData(userData : User)

    @Query("DELETE FROM User WHERE userId = :userId")
    fun deleteUserData(userId: Int)

    @Query("SELECT * FROM User WHERE userId = :userId")
    fun getDataByUserId(userId: Int): User
}