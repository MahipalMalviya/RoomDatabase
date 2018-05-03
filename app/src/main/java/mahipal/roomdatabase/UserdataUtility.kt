package mahipal.roomdatabase

import android.content.Context
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class UserdataUtility {

    companion object {
        private var INSTANCE: UserdataUtility? = null

        fun getInstance(): UserdataUtility{
            if (INSTANCE == null){
                INSTANCE = UserdataUtility()
            }
            return INSTANCE as UserdataUtility
        }
    }
    fun addUserData(userData: User,dataCallback: DatabaseCallback,context: Context){
        Completable.fromAction {
            AppDatabase.getInstance(context).userDao().insertUserData(userData)
        }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    object: CompletableObserver {
                        override fun onComplete() {
                            dataCallback.dataAdded()
                        }

                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onError(e: Throwable) {
                            dataCallback.dataNotAvailable(e.toString())
                        }

                    }
                }
    }

    fun updateUserData(userData: User, dataCallback: DatabaseCallback, context: Context){
        Completable.fromAction{
            AppDatabase.getInstance(context).userDao().updateUserData(userData)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    object: CompletableObserver {
                        override fun onComplete() {
                            dataCallback.dataUpdated()
                        }

                        override fun onSubscribe(d: Disposable) {

                        }

                        override fun onError(e: Throwable) {
                            dataCallback.dataNotAvailable(e.toString())
                        }
                    }
                }
    }

    fun deleteUserData(userId: Int, dataCallback: DatabaseCallback, context: Context) {
        Completable.fromAction {
            AppDatabase.getInstance(context).userDao().deleteUserData(userId)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    object: CompletableObserver {
                        override fun onComplete() {
                            dataCallback.dataRemoved()
                        }

                        override fun onSubscribe(d: Disposable) {

                        }

                        override fun onError(e: Throwable) {
                            dataCallback.dataNotAvailable(e.toString())
                        }

                    }
                }
    }

    fun getUserDataByUserId(userId: Int,dataCallback: DatabaseCallback, context: Context){

        AppDatabase.getInstance(context).userDao().getDataByUserId(userId)
    }

}