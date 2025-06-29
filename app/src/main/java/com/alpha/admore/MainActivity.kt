package com.alpha.admore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alpha.admore.ui.theme.AdMoreSDKTheme
import com.alpha.admoresdk.AdMoreSDK
import com.alpha.admoresdk.presentation.callback.InitCallback

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdMoreSDKTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                    )

                    AdMoreSDK.initialize(
                        context = this,
                        uniqueKey = "ppppppp",callback= object : InitCallback {
                            override fun onSuccess() {
                                AdMoreSDK.sendEvent("event_name", mapOf("key" to "value"))
                            }

                            override fun onError(error: Throwable) {
                                // Handle initialization error
                            }
                        })
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier.clickable {
          //  AdMoreSDK.sendEvent("event_name", mapOf("key" to "value"))
        })
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AdMoreSDKTheme {
        Greeting("Android")
    }
}