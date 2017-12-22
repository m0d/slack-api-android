package pl.hellsoft.slack.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.github.ajalt.timberkt.e
import com.github.ajalt.timberkt.w
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * @author Grzegorz Pawełczuk
 * @email gpawelczuk@hellsoft.pl
 * @since 19.12.2017
 */

class MainActivity : AppCompatActivity() {

    private var disposables : CompositeDisposable = CompositeDisposable()
    private val mSlackApiWrapper by lazy { SlackApiWrapper("xoxp-") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun register(){
        mSlackApiWrapper.init()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    with(mSlackApiWrapper) {
                        connectionListener()
                                .threadSafe()
                                .subscribe({ connected -> w { "connectionListener : $connected" } }, { error ->
                                    e { "Error0 ${error.message}" }
                                })

                        authListener()
                                .threadSafe()
                                .subscribe({ authorized -> w { "authListener : $authorized" } }, { error -> e { "Error1  ${error.message}" } })

                        onMessageReceived()
                                .threadSafe()
                                .subscribe({ message -> w { "onMessageReceived : $message" } }, { error -> e { "Error2 ${error.message}" } })

                        connect()
                                .threadSafe()
                                .subscribe({ connect -> w { "connect : $connect" } }, { error -> e { "Error3 ${error.message}" }; error.printStackTrace() })
                    }
                },{ error : Throwable -> e { "Error5 ${error.message}" }})
    }

    private fun unregister() {
        mSlackApiWrapper.disconnect()
    }

    override fun onPause() {
        super.onPause()
        unregister()
        disposables.clear()
    }

    override fun onResume() {
        super.onResume()
        register()
    }

    override fun onDestroy() {
        if(!disposables.isDisposed) {
            disposables.dispose()
        }
        super.onDestroy()
    }
}

private fun <T> Observable<T>.threadSafe(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
