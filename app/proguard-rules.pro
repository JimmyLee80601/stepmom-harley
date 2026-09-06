# Step-Mom Harley ProGuard Rules
-keep class com.harley.stepmom.** { *; }
-keepclassmembers class com.harley.stepmom.ChatMessage { *; }
-keep class com.google.gson.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**
