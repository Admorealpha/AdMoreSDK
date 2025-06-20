
# Keep SDK entry points
-keep public class com.alpha.admoresdk.AdMoreSDK {
    public *;
}
-keep public class com.alpha.admoresdk.presentation.callback.** {
    *;
}

# Keep Koin - Replace Dagger rules with Koin rules
-keep class org.koin.** { *; }
-keep class org.koin.core.** { *; }
-keep class org.koin.android.** { *; }
-dontwarn org.koin.**

# Keep Kotlin reflection for Koin
-keep class kotlin.Metadata { *; }
-keep class kotlin.reflect.** { *; }
-dontwarn kotlin.reflect.**

# Keep models and data classes
-keep class com.alpha.admoresdk.domain.model.** { *; }
-keep class com.alpha.admoresdk.data.model.** { *; }
-keep class com.alpha.admoresdk.data.source.local.model.** { *; }

# Keep collectors for reflection
-keep class com.alpha.admoresdk.data.source.local.collector.** {
    <init>(...);
    public *;
}

# Keep factory classes
-keep class com.alpha.admoresdk.data.source.local.factory.** { *; }

# Keep repository implementations
-keep class com.alpha.admoresdk.data.repository.** {
    <init>(...);
    public *;
}

# Keep use cases
-keep class com.alpha.admoresdk.domain.usecase.** {
    <init>(...);
    public *;
}

# Keep enum classes
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
    **[] $VALUES;
}

# Keep Kotlin coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}
-dontwarn kotlinx.coroutines.**

# Retrofit and OkHttp rules
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations
-keepattributes AnnotationDefault

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Gson rules
-keep class com.google.gson.** { *; }
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers enum * { *; }

# Google Play Services
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

# Android X
-dontwarn androidx.**
-keep class androidx.** { *; }

# AdMore SDK specific - Keep interface implementations
-keep class * implements com.alpha.admoresdk.domain.repository.** { *; }
-keep class * implements com.alpha.admoresdk.core.encryption.DataEncryptor { *; }

# Keep Logger
-keep class com.alpha.admoresdk.core.logger.Logger { *; }

# Keep network classes
-keep class com.alpha.admoresdk.core.network.** { *; }

# Keep storage classes
-keep class com.alpha.admoresdk.core.storage.** { *; }

# Keep BuildConfig
-keep class com.alpha.admoresdk.BuildConfig { *; }

# Generic rules for data classes with Gson
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Keep annotation attributes
-keepattributes InnerClasses
-keepattributes EnclosingMethod