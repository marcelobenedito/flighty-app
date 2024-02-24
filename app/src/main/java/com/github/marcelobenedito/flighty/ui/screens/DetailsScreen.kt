package com.github.marcelobenedito.flighty.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Liquor
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.marcelobenedito.flighty.R
import com.github.marcelobenedito.flighty.data.model.Airport
import com.github.marcelobenedito.flighty.data.model.Flight
import com.github.marcelobenedito.flighty.ui.theme.Black
import com.github.marcelobenedito.flighty.ui.theme.Blue80

@Composable
fun DetailsScreen(
    flightList: List<Flight>,
    onFavoriteClick: (Flight) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
        Text(
            text = "Flights",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        DetailsList(flightList = flightList, onFavoriteClick = onFavoriteClick)
    }
}

@Composable
fun DetailsItemList(
    flight: Flight,
    onFavoriteClick: (Flight) -> Unit,
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
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Image(
                        painter = painterResource(id = flight.drawableResourceId),
                        contentDescription = null,
                        modifier = Modifier.height(24.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = flight.airplane,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                IconButton(onClick = { onFavoriteClick(flight) }) {
                    Icon(
                        imageVector = if (flight.isFavorite) Icons.Default.Star else Icons.Outlined.StarOutline,
                        contentDescription = null
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.fly_off),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .background(Color.White)
                            .width(2.dp)
                            .fillMaxHeight()
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.land),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = buildAnnotatedString {
                        append("${flight.departureTime} - ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("${flight.departure.name} (${flight.departure.code})")
                        }
                    })
                    Spacer(modifier = Modifier.height(24.dp))
                    Column {
                        Text(text = flight.totalTime)
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            if (flight.isWifiAvailable) {
                                Icon(
                                    imageVector = Icons.Default.Wifi,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            if (flight.isMovieAvailable) {
                                Icon(
                                    imageVector = Icons.Default.LiveTv,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            if (flight.isMealAvailable) {
                                Icon(
                                    imageVector = Icons.Default.Fastfood,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            if (flight.isDrinkAvailable) {
                                Icon(
                                    imageVector = Icons.Default.Liquor,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.land),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = buildAnnotatedString {
                        append("${flight.destinationTime} - ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("${flight.destination.name} (${flight.destination.code})")
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun DetailsList(
    flightList: List<Flight>,
    onFavoriteClick: (Flight) -> Unit,
    modifier: Modifier = Modifier,
    emptyListMessage: String = "No found results 😢"
) {
    if (flightList.isEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = emptyListMessage,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = Black
            )
        }
    } else {
        LazyColumn(modifier = modifier) {
            items(flightList) {
                DetailsItemList(flight = it, onFavoriteClick = onFavoriteClick)
            }
        }
    }
}

@Preview
@Composable
fun DetailsItemListPreview() {
    MaterialTheme {
        Surface {
            val mockData = Flight(
                id = 1,
                departure = Airport(1, "ABC", "ABC Airport", 10),
                departureTime = "5:30",
                destination = Airport(2, "DEF", "DEF Airport", 10),
                destinationTime = "10:40",
                airplane = "Airplane 123",
                drawableResourceId = R.drawable.ic_launcher_background,
                totalTime = "5 hrs 10 min",
                isWifiAvailable = true,
                isMovieAvailable = true,
                isMealAvailable = true,
                isDrinkAvailable = true
            )
            DetailsItemList(flight = mockData, onFavoriteClick = {})
        }
    }
}

@Preview
@Composable
fun DetailsScreenPreview() {
    MaterialTheme {
        Surface {
            val mockData = listOf(
                Flight(
                    id = 1,
                    departure = Airport(1, "ABC", "ABC Airport", 10),
                    departureTime = "5:30",
                    destination = Airport(2, "DEF", "DEF Airport", 10),
                    destinationTime = "10:40",
                    airplane = "Airplane 123",
                    drawableResourceId = R.drawable.ic_launcher_background,
                    totalTime = "5 hrs 10 min",
                    isWifiAvailable = true,
                    isDrinkAvailable = true,
                    isFavorite = true
                ),
                Flight(
                    id = 2,
                    departure = Airport(1, "ABC", "ABC Airport", 10),
                    departureTime = "5:30",
                    destination = Airport(2, "FGH", "FGH Airport", 10),
                    destinationTime = "10:40",
                    airplane = "Airplane 321",
                    drawableResourceId = R.drawable.ic_launcher_background,
                    totalTime = "5 hrs 10 min",
                    isWifiAvailable = true,
                ),
                Flight(
                    id = 3,
                    departure = Airport(1, "IJK", "IJK Airport", 10),
                    departureTime = "5:30",
                    destination = Airport(2, "DEF", "DEF Airport", 10),
                    destinationTime = "10:40",
                    airplane = "Airplane 456",
                    drawableResourceId = R.drawable.ic_launcher_background,
                    totalTime = "5 hrs 10 min",
                    isWifiAvailable = true,
                    isMovieAvailable = true,
                    isMealAvailable = true,
                    isFavorite = true
                ),
                Flight(
                    id = 4,
                    departure = Airport(1, "DEF", "DEF Airport", 10),
                    departureTime = "5:30",
                    destination = Airport(2, "IJK", "IJK Airport", 10),
                    destinationTime = "10:40",
                    airplane = "Airplane 654",
                    drawableResourceId = R.drawable.ic_launcher_background,
                    totalTime = "5 hrs 10 min",
                    isWifiAvailable = true,
                )
            )
            DetailsScreen(flightList = mockData, onFavoriteClick = {})
        }
    }
}