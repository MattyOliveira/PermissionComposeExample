package com.example.permissioncomposeexample

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.permissioncomposeexample.ui.theme.PermissionComposeExampleTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PermissionComposeExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val permissionState = rememberMultiplePermissionsState(permissions = listOf(
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.ACCESS_NETWORK_STATE
                    ))

                    val lificycleOwner = LocalLifecycleOwner.current
                    DisposableEffect(key1 = lificycleOwner, effect = {
                        val observer = LifecycleEventObserver{ _, event ->
                            if (event == Lifecycle.Event.ON_RESUME) {
                                permissionState.launchMultiplePermissionRequest()
                            }
                        }
                        lificycleOwner.lifecycle.addObserver(observer)

                        onDispose {
                            lificycleOwner.lifecycle.removeObserver(observer)
                        }
                    })

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        permissionState.permissions.forEach { perm ->
                            when(perm.permission){
                                Manifest.permission.INTERNET -> {
                                    when {
                                        perm.hasPermission -> { Text(text = "Permissão de internet aceita") }
                                        perm.shouldShowRationale -> { Text(text = "Permissão de internet é precisa pra adentrar o app") }
                                        perm.IsPermanentDenied() -> { Text(text = "Permissão de internet foi negada") }
                                    }

                                }

                                Manifest.permission.ACCESS_NETWORK_STATE -> {
                                    when {
                                        perm.hasPermission -> { Text(text = "Permissão de Network aceita") }
                                        perm.shouldShowRationale -> { Text(text = "Permissão de Network é precisa pra adentrar o app") }
                                        perm.IsPermanentDenied() -> { Text(text = "Permissão de Network foi negada") }
                                    }
                                }

                                Manifest.permission.ACCESS_WIFI_STATE -> {
                                    when {
                                        perm.hasPermission -> { Text(text = "Permissão de WIFI aceita") }
                                        perm.shouldShowRationale -> { Text(text = "Permissão de WIFI é precisa pra adentrar o app") }
                                        perm.IsPermanentDenied() -> { Text(text = "Permissão de WIFI foi negada") }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PermissionComposeExampleTheme {
        Greeting("Android")
    }
}