package com.github.marcelobenedito.flighty.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.marcelobenedito.flighty.R
import com.github.marcelobenedito.flighty.data.model.Airport
import com.github.marcelobenedito.flighty.data.model.Flight
import com.github.marcelobenedito.flighty.ui.theme.Blue80
import com.github.marcelobenedito.flighty.ui.theme.Gray40
import com.github.marcelobenedito.flighty.ui.theme.Gray80

@Composable
fun HomeScreen(
    uiState: FlightyUiState,
    onAirportClick: (Airport) -> Unit,
    onEnterSearchText: (String) -> Unit,
    onFavoriteClick: (Flight) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .background(Gray40)
        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        BasicTextField(
            value = uiState.searchText,
            singleLine = true,
            onValueChange = { onEnterSearchText(it) },
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White, shape = RoundedCornerShape(50.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Gray80
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box {
                        if (uiState.searchText.isEmpty()) {
                            Text(
                                text = "Search by airport...",
                                color = Gray80
                            )
                        }
                        innerTextField()
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = if (uiState.isDisplayFavorite) stringResource(R.string.favorites) else stringResource(R.string.results),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (uiState.isDisplayFavorite) {
            DetailsList(
                flightList = uiState.favorites,
                onFavoriteClick = onFavoriteClick,
                emptyListMessage = "You don't have favorite flights yet.\nYou can search for them and favorite them 🔎"
            )
        } else {
            AirportList(airportList = uiState.airports, onItemListClick = onAirportClick)
        }
    }
}

@Composable
fun AirportItemList(
    airport: Airport,
    onClick: (Airport) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Blue80,
            contentColor = Color.White
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick(airport) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = airport.code,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = airport.name,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun AirportList(
    airportList: List<Airport>,
    modifier: Modifier = Modifier,
    onItemListClick: (Airport) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(airportList) {
            AirportItemList(airport = it, onClick = onItemListClick)
        }
    }
}

@Preview
@Composable
fun AirportItemListPreview() {
    MaterialTheme {
        Surface {
            val mockData = Airport(id = 1, code = "CODE 1", name = "Airport Name 1", passengers = 10)
            AirportItemList(airport = mockData, onClick = {})
        }
    }
}

@Preview(name = "Search results")
@Composable
fun HomeScreenSearchResultsPreview() {
    MaterialTheme {
        Surface {
            val mockedAirports: List<Airport> = listOf(
                Airport(id = 1, code = "CODE 1", name = "Airport Name 1", passengers = 10),
                Airport(id = 2, code = "CODE 2", name = "Airport Name 2", passengers = 10),
                Airport(id = 3, code = "CODE 3", name = "Airport Name 3", passengers = 10),
                Airport(id = 4, code = "CODE 4", name = "Airport Name 4", passengers = 10)
            )
            val mockData = FlightyUiState(airports = mockedAirports)
            HomeScreen(uiState = mockData, onEnterSearchText = {}, onAirportClick = {}, onFavoriteClick = {})
        }
    }
}

@Preview(name = "Favorites")
@Composable
fun HomeScreenFavoritesPreview() {
    MaterialTheme {
        Surface {
            val mockedAirports: List<Airport> = listOf(
                Airport(id = 1, code = "CODE 1", name = "Airport Name 1", passengers = 10),
                Airport(id = 2, code = "CODE 2", name = "Airport Name 2", passengers = 10),
                Airport(id = 3, code = "CODE 3", name = "Airport Name 3", passengers = 10),
                Airport(id = 4, code = "CODE 4", name = "Airport Name 4", passengers = 10)
            )
            val mockData = FlightyUiState(isDisplayFavorite = true, airports = mockedAirports)
            HomeScreen(uiState = mockData, onEnterSearchText = {}, onAirportClick = {}, onFavoriteClick = {})
        }
    }
}
