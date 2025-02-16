package com.leip1493.evolutionygoapp.topplayers.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.leip1493.evolutionygoapp.R
import com.leip1493.evolutionygoapp.components.CustomSelector
import com.leip1493.evolutionygoapp.core.models.Player
import com.leip1493.evolutionygoapp.core.models.Season
import com.leip1493.evolutionygoapp.ui.theme.Background

@ExperimentalMaterial3Api
@Composable
fun TopPlayersScreen(
    topPlayersViewModel: TopPlayersViewModel = hiltViewModel(),
    navigateToPlayerDetail: (String, String, String) -> Unit,
) {
    var isFirstLoading by remember { mutableStateOf(true) }
    val isLoadingPlayers by topPlayersViewModel.isLoadingPlayers.observeAsState(true)
    val isLoadingBanlist by topPlayersViewModel.isLoadingBanlist.observeAsState(true)
    val players by topPlayersViewModel.players.observeAsState(listOf())
    val seasons by topPlayersViewModel.seasons.observeAsState(listOf())
    val banlist by topPlayersViewModel.banlist.observeAsState(listOf())
    val selectedSeason by topPlayersViewModel.selectedSeason.observeAsState(Season(4, "Season 4"))
    val selectedBanlist by topPlayersViewModel.selectedBanlist.observeAsState("")

    if (isFirstLoading && (isLoadingPlayers || isLoadingBanlist)) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Background)
        ) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
        isFirstLoading = false
        return
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = Color(0xFF0F172A),
        ) {
            Column(
                Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
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
                    SeasonSelector(selectedSeason, Modifier.weight(1f), seasons) {
                        topPlayersViewModel.selectSeason(it)
                    }
                    BanlistSelector(selectedBanlist, Modifier.weight(1f), banlist) {
                        topPlayersViewModel.selectBanlist(it)
                    }
                }
                PlayerSections(players) { userId ->
                    navigateToPlayerDetail(userId, selectedSeason.id.toString(), selectedBanlist)
                }
            }
        }
    }


}

@Composable
private fun PlayerSections(
    players: List<Player>,
    navigateToPlayerDetail: (String) -> Unit,
) {
    val topPlayersList = if (players.size <= 4) players else players.slice(0..3)
    val playersTableList = players.slice(4..players.lastIndex)
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (players.isEmpty()) {
            Box(
                Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text(
                    "No players found",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        } else {
            TopPlayers(topPlayersList) {
                navigateToPlayerDetail(it)
            }
            PlayersTable(Modifier.fillMaxWidth(), playersTableList) {
                navigateToPlayerDetail(it)
            }
        }

    }
}

@Composable
private fun TopPlayers(
    players: List<Player>,
    navigateToPlayerDetail: (String) -> Unit,
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(players) { player ->
            TopPlayerCard(modifier = Modifier.width(180.dp), player) {
                navigateToPlayerDetail(it)
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun SeasonSelector(
    selected: Season,
    modifier: Modifier,
    data: List<Season>,
    onSelectedChange: (Season) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded, onExpandedChange = { expanded = it }, modifier = modifier
    ) {
        OutlinedTextField(value = selected.label,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            maxLines = 1,
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White,

                ),
            placeholder = {
                Text("Season", color = Color.White)
            },
            modifier = Modifier.menuAnchor(),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            })
        ExposedDropdownMenu(
            expanded,
            onDismissRequest = { expanded = false },
        ) {
            data.forEach {
                DropdownMenuItem(text = { Text(it.label) }, onClick = {
                    expanded = false
                    onSelectedChange(it)
                })
            }
        }
    }

}

@Composable
private fun BanlistSelector(
    selected: String,
    modifier: Modifier,
    banlist: List<String>,
    onSelectedChange: (String) -> Unit,
) {
    CustomSelector(
        selected,
        modifier = modifier,
        data = banlist,
        placeholder = "Banlist",
        onSelectedChange = onSelectedChange
    )
}

@SuppressLint("DefaultLocale")
@Composable
private fun TopPlayerCard(
    modifier: Modifier,
    player: Player,
    navigateToPlayerDetail: (String) -> Unit,
) {
    val initials = getPlayerInitials(player.name)

    Card(
        border = BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.clickable {
            navigateToPlayerDetail(player.userId)
        }
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
                    .background(LogoColor),
                contentAlignment = Alignment.Center

            ) {
                Text(initials, color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.size(8.dp))
            Row {
                for (i in 1..player.stars) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star",
                        tint = Color.Yellow
                    )
                }
            }
            Spacer(Modifier.size(16.dp))
            Text(
                "#${player.position} ${player.name}",
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
    if (playerName.length < 2) return playerName.uppercase()
    val splittedName = playerName.split(" ")
    val firstName = splittedName[0]
    val lastName = splittedName.getOrNull(1) ?: ""
    val initials = "${firstName.first()}${
        if (lastName.isNotEmpty()) lastName.first() else firstName.slice(
            1..1
        )
    }".uppercase()
    return initials
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScrollablePlayersTable(modifier: Modifier, players: List<Player>) {
    LazyColumn(
        modifier = modifier
            .background(Color(0xff0A1120))
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        stickyHeader {
            PlayerTableHeader()
        }

        if (players.isEmpty()) {
            item {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text("No more players found", color = Color.White)
                }
            }
        } else {
            items(players) { player ->
                PlayerTableContent(player) {

                }
            }

        }


    }
}

@Composable
fun PlayersTable(
    modifier: Modifier,
    players: List<Player>,
    navigateToPlayerDetail: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .background(Color(0xff0A1120))
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        PlayerTableHeader()

        if (players.isEmpty()) {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("No more players found", color = Color.White)
            }
        } else {
            players.forEach { player ->
                PlayerTableContent(player) { navigateToPlayerDetail(it) }
            }
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
            modifier = Modifier.weight(3f),
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
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = "WR(%)",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.weight(1.5f),
            textAlign = TextAlign.Center
        )
    }

}

@Composable
private fun PlayerTableContent(player: Player, navigateToPlayerDetail: (String) -> Unit) {
    val initials = getPlayerInitials(player.name)
    Row(
        Modifier
            .fillMaxWidth()
            .clickable {
                navigateToPlayerDetail(player.userId)
            },
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = player.position.toString(),
            Modifier.weight(.5f),
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Row(
            Modifier
                .weight(3f),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
//            Box(
//                modifier = Modifier
//                    .size(40.dp)
//                    .clip(CircleShape)
//                    .background(LogoColor),
//                contentAlignment = Alignment.Center
//
//            ) {
//                Text(initials, color = Color.White, fontWeight = FontWeight.Bold)
//            }
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = player.name,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    fontSize = 14.sp
                )
                Row {
                    for (i in 1..player.stars) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star",
                            tint = Color.Yellow,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }

            }
        }
        Text(
            text = player.points.toString(),
            Modifier.weight(1f),
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Text(
            text = player.wins.toString(),
            Modifier.weight(1f),
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Text(
            text = player.losses.toString(),
            Modifier.weight(1f),
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Text(
            text = player.winRate,
            Modifier.weight(1.5f),
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

private val LogoColor = Color(0xFF1565C0)