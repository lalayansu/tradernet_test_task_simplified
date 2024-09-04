package com.example.tradernet_test_task_simplified.presenter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tradernet_test_task_simplified.presenter.home.HomeScreen
import com.example.tradernet_test_task_simplified.presenter.navigation.Destinations
import com.example.tradernet_test_task_simplified.presenter.theme.TradernetTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TradernetApp(
    startRoute: String
) {
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(state = rememberTopAppBarState())

    TradernetTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                NavHost(
                    modifier = Modifier
                        .imePadding()
                        .navigationBarsPadding()
                        .statusBarsPadding()
                        .padding(paddingValues = PaddingValues(0.dp)),
                    navController = navController,
                    startDestination = startRoute,
                ) {
                    composable(Destinations.HOME_SCREEN_DESTINATION) {
                        HomeScreen()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TradernetAppPreview() {
    TradernetApp(
        startRoute = Destinations.HOME_SCREEN_DESTINATION,
    )
}