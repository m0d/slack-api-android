##### --- Slack RTM slack-api-android ---
-keep class com.fasterxml.jackson.databind.ObjectMapper {
    public <methods>;
    protected <methods>;
}
-keep class com.fasterxml.jackson.databind.ObjectWriter {
    public ** writeValueAsString(**);
}
-keepnames class com.fasterxml.jackson.** { *; }
-dontwarn com.fasterxml.jackson.databind.**
-dontwarn allbegray.slack.**
-keep class allbegray.slack.** {*;}
-dontwarn okio.**
-keep class okio.** {*;}
-dontwarn com.squareup.okhttp3.**
-dontwarn com.squareup.okhttp.internal.**
-dontwarn okhttp3.**
-keep class pl.hellsoft.slack.wrapper.model.** { *; }

-dontwarn org.xmlpull.v1.**
-dontwarn kotlin.reflect.jvm.internal.**

#### Retrofit
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions
-keep class allbegray.slack.webapi.retrofit.SlackWebApiClientImplv2.** { *; }