
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
-dontwarn okhttp3.**
