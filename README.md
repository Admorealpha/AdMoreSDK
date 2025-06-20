# AdMore SDK for Android

[![](https://jitpack.io/v/Admorealpha/AdMoreSDK.svg)](https://jitpack.io/#Admorealpha/AdMoreSDK)
[![API](https://img.shields.io/badge/API-26%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=26)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

AdMore SDK is a comprehensive Android library for advanced data collection and analytics. Built with modern Android architecture patterns, it provides deep insights into user behavior, device characteristics, and app performance while maintaining the highest standards of privacy and security.

## 🚀 Features

- **📊 Comprehensive Analytics**: Device info, network analytics, user behavior tracking
- **🔒 Privacy-First**: Granular permission management with user consent
- **⚡ Real-time Data**: Instant event tracking with offline support
- **🔐 Enterprise Security**: RSA/AES hybrid encryption for data protection
- **🛡️ Conflict-Free**: Works alongside existing analytics libraries
- **📱 Modern Architecture**: Clean architecture with Koin dependency injection
- **🌐 Network Resilient**: Automatic retry with intelligent caching

## 📱 Requirements

- **Minimum SDK**: Android 8.0 (API level 26)
- **Target SDK**: Android 14 (API level 34)
- **Kotlin**: 1.9.0+
- **Gradle**: 8.0+

## 📥 Installation

### Step 1: Add JitPack Repository

Add JitPack repository to your project's `build.gradle` (Project level):

```kotlin
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

Or if using `settings.gradle` (Gradle 7.0+):

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2: Add Dependency

Add the SDK to your app's `build.gradle` (Module level):

```kotlin
dependencies {
    implementation 'com.github.Admorealpha:AdMoreSDK:1.0.0'
}
```

### Step 3: Sync Project

Sync your project with Gradle files.

## 🛠️ Quick Start

### Basic Integration

#### 1. Initialize SDK

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize AdMore SDK
        AdMoreSDK.initialize(
            context = this,
            uniqueKey = "your-unique-app-identifier"
        ) {
            // Success callback - SDK is ready to use
            Log.d("AdMore", "SDK initialized successfully")
        }
    }
}
```

#### 2. Send Custom Events

```kotlin
// Track user login
AdMoreSDK.sendEvent(
    eventName = "user_login",
    data = mapOf(
        "user_id" to "12345",
        "login_method" to "email",
        "timestamp" to System.currentTimeMillis()
    )
)

// Track screen views
AdMoreSDK.sendEvent(
    eventName = "screen_view",
    data = mapOf(
        "screen_name" to "ProductDetails",
        "product_id" to "prod_789"
    )
)

// Track purchases
AdMoreSDK.sendEvent(
    eventName = "purchase",
    data = mapOf(
        "transaction_id" to "txn_456",
        "amount" to 99.99,
        "currency" to "USD"
    )
)
```

#### 3. Check SDK Status

```kotlin
if (AdMoreSDK.isInitialized()) {
    // SDK is ready for use
    AdMoreSDK.sendEvent("app_opened", mapOf("source" to "main_activity"))
}
```

## 📋 Permissions

### Required Permissions (Automatically Included)

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="com.google.android.gms.permission.AD_ID" />
```


### Application Class Integration

For best performance, initialize the SDK in your Application class:

```kotlin
class MyApplication : AppCompactActivity() {
    override fun onCreate() {
        super.onCreate()

        // Initialize SDK once in Application class
        AdMoreSDK.initialize(
            context = this,
            uniqueKey = "your-unique-key"
        ) { error ->
            if (error != null) {
                Log.e("AdMore", "SDK initialization failed: ${error.message}")
            } else {
                Log.d("AdMore", "SDK initialized successfully")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cleanup SDK resources (optional)
        AdMoreSDK.cleanup()
    }
}
```


### Error Handling

```kotlin
AdMoreSDK.initialize(
    context = applicationContext,
    uniqueKey = "your-app-key"
) { error ->
    when (error) {
        is SecurityException -> {
            Log.e("AdMore", "Permission denied: ${error.message}")
            // Handle permission issues
        }
        is IllegalArgumentException -> {
            Log.e("AdMore", "Invalid configuration: ${error.message}")
            // Handle configuration errors
        }
        else -> {
            Log.e("AdMore", "Initialization failed: ${error.message}")
            // Handle other errors
        }
    }
}
```

### Event Callbacks

```kotlin
AdMoreSDK.sendEvent(
    eventName = "important_action",
    data = mapOf("action_type" to "user_interaction")
) { error ->
    if (error != null) {
        Log.e("AdMore", "Failed to send event: ${error.message}")
        // Implement fallback or retry logic
    } else {
        Log.d("AdMore", "Event tracked successfully")
    }
}
```

## 📊 Data Collection

### Automatic Data Collection

The SDK automatically collects:

- **Device Information**: Manufacturer, model, OS version, hardware specs
- **App Information**: Package name, version, installation date
- **Network Information**: Connection type, carrier, signal strength
- **Performance Metrics**: Battery status, memory usage, storage info

## 🛡️ ProGuard/R8 Configuration

If using code obfuscation, add these rules to your `proguard-rules.pro`:

```proguard
# AdMore SDK
-keep public class com.alpha.admoresdk.AdMoreSDK {
    public *;
}
-keep public interface com.alpha.admoresdk.presentation.callback.* {
    *;
}

# Koin
-keep class org.koin.** { *; }
-dontwarn org.koin.**

# Retrofit & OkHttp
-keepattributes Signature
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-dontwarn okhttp3.**

# Gson
-keep class com.google.gson.** { *; }
-keepattributes *Annotation*
```



## 🔍 Troubleshooting

### Common Issues

#### SDK Not Initializing

```kotlin
// ❌ Wrong - using Application context
AdMoreSDK.initialize(applicationContext, "key")

// ✅ Correct - using Activity context
AdMoreSDK.initialize(this, "key")

```

#### Events Not Sending

```kotlin
// Ensure SDK is initialized first
if (!AdMoreSDK.isInitialized()) {
    Log.w("AdMore", "SDK not initialized - call initialize() first")
    return
}

// Check network connectivity
// SDK automatically retries when network is available
```


### Performance Tips

- Initialize SDK in Application class for best performance
- Use application context, not activity context
- Grant permissions contextually to improve user experience
- Monitor app performance impact (SDK uses <1% battery)

## 📚 Examples

### E-commerce App Integration

```kotlin
class ECommerceApp : Application() {
    override fun onCreate() {
        super.onCreate()

        AdMoreSDK.initialize(this, "ecommerce-app-v1") {
            // Track app initialization
            AdMoreSDK.sendEvent("app_started", mapOf(
                "version" to BuildConfig.VERSION_NAME,
                "install_source" to getInstallSource()
            ))
        }
    }
}

// In your activities
class ProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Track product views
        AdMoreSDK.sendEvent("product_viewed", mapOf(
            "product_id" to intent.getStringExtra("product_id"),
            "category" to intent.getStringExtra("category"),
            "price" to intent.getDoubleExtra("price", 0.0)
        ))
    }
}

// Track purchases
fun onPurchaseCompleted(transactionId: String, amount: Double) {
    AdMoreSDK.sendEvent("purchase_completed", mapOf(
        "transaction_id" to transactionId,
        "amount" to amount,
        "currency" to "USD",
        "payment_method" to "credit_card"
    ))
}
```

### Gaming App Integration

```kotlin
class GameApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AdMoreSDK.initialize(this, "awesome-game-v2") {
            // Track game initialization
            AdMoreSDK.sendEvent("game_started", mapOf(
                "player_id" to getPlayerId(),
                "game_mode" to "campaign"
            ))
        }
    }
}

// Track game events
fun onLevelCompleted(level: Int, score: Int, duration: Long) {
    AdMoreSDK.sendEvent("level_completed", mapOf(
        "level" to level,
        "score" to score,
        "duration_seconds" to duration / 1000,
        "difficulty" to getCurrentDifficulty()
    ))
}

fun onInAppPurchase(itemId: String, price: Double) {
    AdMoreSDK.sendEvent("iap_purchase", mapOf(
        "item_id" to itemId,
        "price" to price,
        "currency" to "USD",
        "player_level" to getCurrentPlayerLevel()
    ))
}
```

## 🆕 Changelog

### Version 1.0.0 (Latest)
- Initial public release
- Comprehensive data collection capabilities
- Privacy-first design with granular permissions
- Enterprise-grade security with hybrid encryption
- Koin dependency injection for conflict prevention
- Offline support with intelligent caching
- Real-time analytics with custom event tracking

## 📄 License

```
Copyright 2024 AdMore Alpha

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guidelines](CONTRIBUTING.md) for details.

## 🆘 Support

- **Documentation**: [Developer Guide](https://github.com/Admorealpha/AdMoreSDK/wiki)
- **Issues**: [GitHub Issues](https://github.com/Admorealpha/AdMoreSDK/issues)
- **Email**: a.eissa@blueride.co
- **Response Time**: 24-48 hours

## 🔗 Links

- [JitPack Repository](https://jitpack.io/#Admorealpha/AdMoreSDK)
- [Release Notes](https://github.com/Admorealpha/AdMoreSDK/releases)
- [API Documentation](https://github.com/Admorealpha/AdMoreSDK/wiki/API-Reference)
- [Sample Projects](https://github.com/Admorealpha/AdMoreSDK/tree/main/samples)

---

**Made with ❤️ by AdMore Alpha Team**