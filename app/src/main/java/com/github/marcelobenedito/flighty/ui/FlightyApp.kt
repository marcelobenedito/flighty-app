package com.github.marcelobenedito.flighty.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.marcelobenedito.flighty.ui.screens.DetailsScreen
import com.github.marcelobenedito.flighty.ui.screens.HomeScreen
import com.github.marcelobenedito.flighty.ui.screens.FlightyViewModel
import com.github.marcelobenedito.flighty.ui.screens.FlightyUiState
import com.github.marcelobenedito.flighty.ui.theme.Black
import com.github.marcelobenedito.flighty.ui.theme.Gray40
import com.github.marcelobenedito.flighty.ui.theme.Gray60
import com.github.marcelobenedito.flighty.ui.theme.Gray80

enum class FlightyAppScreen { START, DETAILS }

@Composable
fun FlightyApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: FlightyViewModel = viewModel(factory = FlightyViewModel.Factory)
) {
    val uiState: FlightyUiState by viewModel.uiState.collectAsState()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = FlightyAppScreen.valueOf(
        backStackEntry?.destination?.route ?: FlightyAppScreen.START.name
    )
    val appBarTitle = when (currentScreen) {
        FlightyAppScreen.START -> null
        FlightyAppScreen.DETAILS -> if (uiState.selectedAirport == null) "Details" else "${uiState.selectedAirport!!.code} - ${uiState.selectedAirport!!.name}"
    }

    Scaffold(
        topBar = { FlightyTopAppBar(
            title = appBarTitle,
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = { navController.navigateUp() }
        ) },
        containerColor = Gray40,
        modifier = modifier
    ) {
        NavHost(
            navController = navController,
            startDestination = FlightyAppScreen.START.name,
            modifier = Modifier.padding(it)
        ) {
            composable(route = FlightyAppScreen.START.name) {
                HomeScreen(
                    uiState = uiState,
                    onEnterSearchText = viewModel::onEnterSearchText,
                    onFavoriteClick = viewModel::favoriteFlight,
                    onAirportClick = {
                        viewModel.selectAirport(it)
                        navController.navigate(FlightyAppScreen.DETAILS.name)
                    }
                )
            }
            composable(route = FlightyAppScreen.DETAILS.name) {
                DetailsScreen(
                    flightList = uiState.flights,
                    onFavoriteClick = viewModel::favoriteFlight
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightyTopAppBar(
    title: String?,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Gray40),
        title = {
            if (canNavigateBack && title != null) {
                Text(text = title)
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Gray60,
                        modifier = Modifier
                            .background(color = Color.White, shape = RoundedCornerShape(50.dp))
                            .padding(12.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Hello \uD83D\uDC4B",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Gray80
                        )
                        Text(
                            text = "Welcome to Flighty!",
                            style = MaterialTheme.typography.titleSmall,
                            color = Black
                        )
                    }
                }
            }
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        },
        modifier = modifier
    )
}
