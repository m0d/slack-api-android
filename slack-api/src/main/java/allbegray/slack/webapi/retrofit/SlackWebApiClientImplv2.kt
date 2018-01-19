package allbegray.slack.webapi.retrofit

import allbegray.slack.BuildConfig
import allbegray.slack.webapi.SlackWebApiClientImpl
import allbegray.slack.webapi.SlackWebApiConstants
import android.util.Log
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Maciej Madetko
 * @email maciej.madetko@ftlearning.com
 * Nikkei FT Learning Limited
 * @since 19/01/2018.
 */
class SlackWebApiClientImplv2(private val slackToken: String) : SlackWebApiClientImpl(slackToken) {

    private val timeoutInSeconds = 6L
    private var service: SlackService


    init {
        val okHttpClient = prepareOkHttpBuilder().build()

        service = Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .baseUrl(SlackWebApiConstants.SLACK_WEB_API_URL + "/")
                .build()
                .create(SlackService::class.java)
    }

    private fun prepareOkHttpBuilder() : OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
                .connectTimeout(timeoutInSeconds, TimeUnit.SECONDS)
                .writeTimeout(timeoutInSeconds, TimeUnit.SECONDS)
                .readTimeout(timeoutInSeconds, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }
        return builder
    }



    override fun postMessage(channel: String, text: String): String {
        service
                .postMessage(slackToken, channel, text)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            response -> Log.d(SlackWebApiClientImplv2::class.java.name, response.ts)
                        },
                        {
                            error -> Log.d(SlackWebApiClientImplv2::class.java.name, error.toString())
                        }
                )

        return ""
    }
}