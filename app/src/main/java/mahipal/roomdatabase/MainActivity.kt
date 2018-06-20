package mahipal.roomdatabase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var userDatabase: AppDatabase? = null
    private lateinit var dbWorkerThread: WorkerThread

    private val uiHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbWorkerThread = WorkerThread("dbWorkerThread")
        dbWorkerThread.start()

        btn_add.setOnClickListener(this)
        btn_read.setOnClickListener(this)

        userDatabase = AppDatabase.getInstance(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_add -> {
                val user = User(et_id.text.toString().toInt(),
                        et_name.text.toString(),
                        et_emailId.text.toString(),
                        et_contactNo.text.toString())

                insertUserDataInDb(user)
            }
            R.id.btn_read -> {
                fetchDataFromDb(et_id.text.toString().toInt())
            }
        }
    }

    private fun fetchDataFromDb(userId:Int) {
        val task = Runnable {
            val userData = userDatabase?.userDao()?.getDataByUserId(userId)
            uiHandler.post({

                userData?.let {
                    setDataToView(it)
                }?: Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show()
            })
        }
        dbWorkerThread.postTask(task)
    }

    private fun setDataToView(userData: User?) {
        tv_name?.text = userData?.userName
        tv_emailId?.text = userData?.emailId
        tv_contactNo?.text = userData?.phoneNumber
    }

    private fun insertUserDataInDb(user: User) {
        val task = Runnable { userDatabase?.userDao()?.insertUserData(user) }
        dbWorkerThread.postTask(task)
    }

    override fun onDestroy() {
        AppDatabase.destroyInstance()
        dbWorkerThread.quit()
        super.onDestroy()
    }
}
