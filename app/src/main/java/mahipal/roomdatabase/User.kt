package mahipal.roomdatabase

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "user")
data class User(@PrimaryKey(autoGenerate = false) var userId: Int?,
                var userName: String?,
                var emailId: String?,
                var phoneNumber: String?) {
}
