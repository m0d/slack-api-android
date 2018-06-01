##### --- Slack RTM slack-api-android ---
-dontwarn allbegray.slack.**
-keep class allbegray.slack.** {*;}
-dontwarn okio.**
-keep class okio.** {*;}
-dontwarn com.squareup.okhttp3.**
-dontwarn com.squareup.okhttp.internal.**
-dontwarn okhttp3.**

-dontwarn org.xmlpull.v1.**
-dontwarn kotlin.reflect.jvm.internal.**

#### Retrofit
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

-keep class pl.hellsoft.slack.wrapper.model.** { *; }
-keep class pl.hellsoft.slack.wrapper.rtm.model.** { *; }

-dontnote com.google.gson.**

-dontwarn javax.annotation.**
-keepattributes *Annotation*