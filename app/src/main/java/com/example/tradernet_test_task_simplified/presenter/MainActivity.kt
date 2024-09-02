package com.example.tradernet_test_task_simplified.presenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tradernet_test_task_simplified.presenter.navigation.Destinations
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TradernetApp(
                startRoute = Destinations.HOME_SCREEN_DESTINATION,
            )
        }
    }
}