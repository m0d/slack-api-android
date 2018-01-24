# slack-api-android [![Release](https://jitpack.io/v/m0d/slack-api-android.svg)](https://jitpack.io/#m0d/slack-api-android) [![Build Status](https://travis-ci.org/m0d/slack-api-android.svg)](https://travis-ci.org/m0d/slack-api-android)

Port of [pschroen](https://github.com/pschroen)'s [slack-api](https://github.com/pschroen/slack-api-android).

https://jitpack.io/#m0d/slack-api-android

Add it to your build.gradle with:

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

and:

```gradle
dependencies {
        implementation 'com.github.m0d:slack-api-android:1.6.2'

        // all dependencies below are lib compileOnly
        implementation "com.squareup.okhttp3:okhttp:$okhttp_version" 
        implementation "com.squareup.okhttp3:logging-interceptor:$okhttp_version"
        implementation "io.reactivex.rxjava2:rxandroid:$rxandroid2_version" 
        implementation "io.reactivex.rxjava2:rxjava:$rxjava2_version" 
        implementation "com.github.ajalt:timberkt:$timberkt_version" 
        implementation "com.google.code.gson:gson:$gson_version"
        implementation "com.squareup.retrofit2:adapter-rxjava2:$retrofit2_version"
        implementation "com.squareup.retrofit2:retrofit:$retrofit2_version"
        implementation "com.squareup.retrofit2:converter-gson:$retrofit2_version"
}
```

## Differences

- AS 3.x support
- Kotlin wrapper
- Using Retrofit2 & OkHttp under the hood
- RX wrapper (Connection, Auth, Messages only - 4 now)
- POJO deserialization
- sample project

## Example usage

```kotlin
class MainActivity : AppCompatActivity() {

    private var disposables : CompositeDisposable = CompositeDisposable()
    /* https://api.slack.com/custom-integrations/legacy-tokens */
    private val mSlackApiWrapper by lazy { SlackApiWrapper() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun register(){
        val disposable = mSlackApiWrapper.init("xoxp-XXXXXXXX")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ event ->
                    when (event) {
                        is AuthEvent -> d { "AuthEvent received: $event" }
                        is ConnectionEvent -> d { "ConnectionEvent received: $event" }
                        is MessageEvent -> d { "MessageEvent received: $event" }
                        else -> d { "TODO Event received: $event" }
                    }
                }, { error: Throwable -> e { "Error ${error.message}" } })
        disposables.add(disposable)
    }

    override fun onPause() {
        super.onPause()
        mSlackApiWrapper.disconnect()
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
```


## ProGuard

```
-dontwarn okio.**
-keep class okio.** {*;}
-dontwarn com.squareup.okhttp3.**
-dontwarn com.squareup.okhttp.internal.**
-dontwarn okhttp3.**

-dontwarn kotlin.reflect.jvm.internal.**

-dontnote retrofit2.Platform
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

-keep class pl.hellsoft.slack.wrapper.model.** { *; }

-dontnote com.google.gson.**
-dontwarn javax.annotation.**
-keepattributes *Annotation*
```
