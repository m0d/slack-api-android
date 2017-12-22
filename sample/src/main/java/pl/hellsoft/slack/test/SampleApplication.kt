package pl.hellsoft.slack.test

import android.app.Application
import com.github.ajalt.timberkt.Timber


/**
 * @author Grzegorz Pawełczuk
 * @email gpawelczuk@hellsoft.pl
 * @since 19.12.2017
 */

open class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        plantUtils()
    }

    @Suppress("ConstantConditionIf")
    private fun plantUtils() {
        if (BuildConfig.DEBUG) {
            Timber.plant(timber.log.Timber.DebugTree())
        }
    }

}
