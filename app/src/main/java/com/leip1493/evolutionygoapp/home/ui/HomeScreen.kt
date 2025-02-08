@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)

package com.leip1493.evolutionygoapp.home.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leip1493.evolutionygoapp.R
import com.leip1493.evolutionygoapp.components.CustomSelector
import com.leip1493.evolutionygoapp.ui.theme.Background

@Composable
fun HomeScreen() {
    val players = listOf(
        Player("Akane", 999, 999, 0, "75.05%"),
        Player("TheGhost9103", 800, 999, 0, "70.00%"),
        Player("Ene", 700, 999, 0, "65.51%"),
        Player("Ene", 659, 999, 0, "50.05%"),
        Player("Lorem", 659, 999, 0, "50.05%"),
        Player("Ipsum", 659, 999, 0, "50.05%"),
        Player("Foo", 659, 999, 0, "50.05%"),
        Player("Bar", 659, 999, 0, "50.05%"),
        Player("Yugi", 659, 999, 0, "50.05%"),
        Player("Kaiba", 659, 999, 0, "50.05%"),
        Player("Joey", 659, 999, 0, "50.05%"),
    )

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = Color(0xFF0F172A),
        ) {
            Column(
                Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Top players", color = Color.White, fontSize = 32.sp)
                Text(
                    stringResource(R.string.home_description),
                    color = Color(0xFFE5E7EB),
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SeasonSelector(Modifier.weight(1f))
                    BanlistSelector(Modifier.weight(1f))
                }
                TopPlayers(players.take(4))
                PlayerTable(Modifier.fillMaxWidth(), players.subList(4, players.size))
            }
        }
    }
}

@Composable
private fun TopPlayers(players: List<Player>) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        itemsIndexed(players) { index, player ->
            TopPlayerCard(modifier = Modifier.width(180.dp), player, index + 1)
        }
    }
}

@Composable
private fun SeasonSelector(modifier: Modifier) {
    val seasons = listOf("Season 1", "Season 2", "Season 3", "Season 4")

    CustomSelector(modifier = modifier, data = seasons, placeholder = "Season")
}

@Composable
private fun BanlistSelector(modifier: Modifier) {
    val banlist = listOf("Banlist 1", "Banlist 2", "Banlist 3")

    CustomSelector(modifier = modifier, data = banlist, placeholder = "Banlist")
}

@SuppressLint("DefaultLocale")
@Composable
private fun TopPlayerCard(modifier: Modifier, player: Player, position: Int) {
    val initials = getPlayerInitials(player.name)

    Card(
        border = BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = modifier
                .background(Background)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1565C0)),
                contentAlignment = Alignment.Center

            ) {
                Text(initials, color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.size(8.dp))
            Row {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star",
                    tint = Color.Yellow
                )
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star",
                    tint = Color.Yellow
                )
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star",
                    tint = Color.Yellow
                )
            }
            Spacer(Modifier.size(16.dp))
            Text(
                "#$position ${player.name}",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,

                )
            Text(
                "Points: ${player.points}",
                color = Color.White,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp
            )
            Text(
                "Wins: ${player.wins}",
                color = Color.Green,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp
            )
            Text(
                "Losses: ${player.losses}",
                color = Color.Red,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp
            )
            Text(
                "Win Rate: ${player.winRate}",
                color = Color.White,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp
            )

        }
    }
}

private fun getPlayerInitials(playerName: String): String {
    val splittedName = playerName.split(" ")
    val firstName = splittedName[0]
    val lastName = splittedName.getOrNull(1) ?: ""
    val initials =
        "${firstName.first()}${if (lastName.isNotEmpty()) lastName else firstName.slice(1..1)}".uppercase()
    return initials
}

@Composable
fun PlayerTable(modifier: Modifier, players: List<Player>) {
    LazyColumn(
        modifier = modifier
            .background(Color(0xff0A1120))
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        stickyHeader {
            PlayerTableHeader()
        }

        itemsIndexed(players) { index, player ->
            PlayerTableContent(index, player)
        }

    }
}


@Composable
private fun PlayerTableHeader() {
    Row(
        Modifier
            .background(Color(0xff0A1120))
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "#",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.weight(.5f),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Username",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.Center
        )
        Text(
            text = "💰",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = "😎",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = "☠",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.weight(1.1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = "WR(%)",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.weight(1.25f),
            textAlign = TextAlign.Center
        )
    }

}


@Composable
private fun PlayerTableContent(index: Int, player: Player) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = (index + 5).toString(), Modifier.weight(.5f), color = Color.White,
            textAlign = TextAlign.Center
        )
        Text(
            text = player.name,
            Modifier.weight(2f),
            color = Color.White,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            textAlign = TextAlign.Center
        )
        Text(
            text = player.points.toString(),
            Modifier.weight(1f),
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Text(
            text = player.wins.toString(), Modifier.weight(1f), color = Color.White,
            textAlign = TextAlign.Center
        )
        Text(
            text = player.losses.toString(),
            Modifier.weight(1.1f),
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Text(
            text = player.winRate,
            Modifier.weight(1.25f),
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

data class Player(
    val name: String,
    val points: Int,
    val wins: Int,
    val losses: Int,
    val winRate: String
)