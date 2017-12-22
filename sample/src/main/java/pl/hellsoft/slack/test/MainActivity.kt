package pl.hellsoft.slack.test

import allbegray.slack.wrapper.SlackApiWrapper
import allbegray.slack.wrapper.model.AuthEvent
import allbegray.slack.wrapper.model.ConnectionEvent
import allbegray.slack.wrapper.model.MessageEvent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.github.ajalt.timberkt.d
import com.github.ajalt.timberkt.e
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * @author Grzegorz Pawełczuk
 * @email grzegorz.pawelczuk@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 22.12.2017
 */

class MainActivity : AppCompatActivity() {

    private var disposables : CompositeDisposable = CompositeDisposable()
    /* https://api.slack.com/custom-integrations/legacy-tokens */
    private val mSlackApiWrapper by lazy { SlackApiWrapper("xoxp-XXXXXXXX") }

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
                .subscribe({ event ->
                    when(event){
                        is AuthEvent -> d{ "AuthEvent received: $event" }
                        is ConnectionEvent -> d{ "ConnectionEvent received: $event" }
                        is MessageEvent -> d{ "MessageEvent received: $event" }
                        else -> d{ "TODO Event received: $event" }
                    }
                },{ error : Throwable -> e { "Error ${error.message}" }})
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
