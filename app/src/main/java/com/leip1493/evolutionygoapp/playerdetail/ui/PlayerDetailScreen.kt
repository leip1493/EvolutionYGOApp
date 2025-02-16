package com.leip1493.evolutionygoapp.playerdetail.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.leip1493.evolutionygoapp.core.models.Match
import com.leip1493.evolutionygoapp.core.models.Player
import com.leip1493.evolutionygoapp.core.navigation.PlayerDetail
import com.leip1493.evolutionygoapp.ui.theme.Background
import kotlinx.coroutines.launch

private const val INITIAL_TAB = 1

@Composable
fun PlayerDetailScreen(
    playerDetail: PlayerDetail,
    playerDetailViewModel: PlayerDetailViewModel = hiltViewModel(),
) {
    val playerStats by playerDetailViewModel.playerStats.observeAsState()
    val playerMatches by playerDetailViewModel.playerMatches.observeAsState(emptyList())
    val isLoadingPlayerStats by playerDetailViewModel.isLoadingPlayerStats.observeAsState(true)
    val isLoadingPlayerMatches by playerDetailViewModel.isLoadingPlayerMatches.observeAsState(
        true
    )
    var selectedTabIndex by remember { mutableIntStateOf(INITIAL_TAB) }
    val tabs = listOf(
        TabItem("Duels history", { PlayerMatches(playerMatches) }),
        TabItem("Achievements", { AchievementsList() })
    )
    val pagerState = rememberPagerState(INITIAL_TAB) { tabs.size }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        playerDetailViewModel.getPlayerStats(
            playerDetail.id, playerDetail.season, playerDetail.banlist
        )
        playerDetailViewModel.getPlayerMatches(
            playerDetail.id, playerDetail.season, playerDetail.banlist
        )
    }
    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }
    if (isLoadingPlayerStats || isLoadingPlayerMatches) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Background)
        ) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
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
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                PlayerCard(Modifier.fillMaxWidth(), playerStats!!)
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFF9D5CFF),
                ) {
                    tabs.forEachIndexed { index, item ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = {
                                scope.launch { pagerState.animateScrollToPage(index) }
                                selectedTabIndex = index
                            },
                            text = { Text(item.title) }
                        )
                    }
                }
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        tabs[it].content()
                    }
                }
            }
        }
    }

}

@Composable
fun PlayerMatches(matches: List<Match>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (matches.isEmpty()) {
            Text(
                "No matches found",
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 24.dp)
            )
        } else {
            LazyColumn(
                Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(matches) {
                    PlayerMatch(it)
                }
            }
        }

    }
}

@Composable
fun PlayerMatch(match: Match) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, CardBorder, RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground,
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Season ${match.season}",
                    color = TextSecondary,
                )
                Text(text = match.date, color = TextSecondary)
            }
            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Mode:",
                    color = TextSecondary,
                )
                Text(text = match.duelMode, color = TextPrimary)
            }
            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Type:",
                    color = TextSecondary,
                )
                Text(text = match.type, color = TextPrimary)
            }
            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Banlist:",
                    color = TextSecondary,
                )
                Text(text = match.banlist, color = TextPrimary)
            }
            HorizontalDivider(color = CardBorder)
            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Players:",
                        color = TextSecondary,
                    )
                    match.playerNames.forEach {
                        Text(text = it, color = TextPrimary)
                    }
                }
                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    Text(
                        text = "Opponents:",
                        color = TextSecondary,
                    )
                    match.opponentNames.forEach {
                        Text(
                            text = it, color = TextPrimary, textAlign = TextAlign.End
                        )
                    }
                }
            }
            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${match.result} (${match.points} pts)",
                    color = if (match.points > 0) ResultPositive else ResultNegative,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun PlayerCard(
    modifier: Modifier,
    player: Player,
) {
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

private val LogoColor = Color(0xFF1565C0)
private val CardBackground = Color(0x4D1F2937)
private val TextPrimary = Color(0xFFe5e7eb)
private val TextSecondary = Color(0xff9ca3af)
private val CardBorder = Color(0xFF374151)
private val ResultPositive = Color(0xFF34d399)
private val ResultNegative = Color(0xFFF87171)

object GameColors {
    val Background = Color(0xFF0A0D16)
    val Purple = Color(0xFF9D5CFF)
    val Cyan = Color(0xFF00E5FF)
    val CardBackground = Color(0x4D1F2937)
    val BorderColor = Color(0xFF374151)
    val TextPrimary = Color(0xFFE5E7EB)
    val TextSecondary = Color(0xFF9CA3AF)
    val Green = Color(0xFF34D399)
    val Red = Color(0xFFF87171)
    val Yellow = Color(0xFFFFD700)
}

@Composable
fun AchievementsList() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(getAchievements()) { achievement ->
            AchievementCard(achievement)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AchievementCard(achievement: Achievement) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = GameColors.CardBackground)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF9D5CFF).copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    achievement.icon,
                    fontSize = 24.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    achievement.name,
                    color = GameColors.TextPrimary,
                    fontWeight = FontWeight.Medium
                )
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Points earned: ${achievement.earnedPoints}",
                        color = GameColors.Green,
                        fontSize = 14.sp
                    )
                    Text(
                        "Unlocked: ${achievement.unlockedAt}",
                        color = GameColors.TextPrimary,
                        fontSize = 14.sp
                    )
                }
                Text(achievement.description, color = GameColors.TextSecondary, fontSize = 14.sp)

                Spacer(Modifier.size(8.dp))

                FlowRow(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    achievement.labels.forEach {
                        Badge(
                            containerColor = Background,
                            contentColor = GameColors.TextPrimary,
                            modifier = Modifier
                                .border(1.dp, GameColors.TextPrimary, RoundedCornerShape(50))
                        ) {
                            Text(it, fontSize = 14.sp, modifier = Modifier.padding(4.dp))
                        }
                    }
                }
            }
        }
    }
}

data class TabItem(
    val title: String,
    val content: @Composable () -> Unit,
)

data class AchievementDTO(
    val id: Int,
    val icon: String,
    val name: String,
    val labels: List<String>,
    val unlockedAt: String,
    val description: String,
    val earnedPoints: Int,
)

private fun AchievementDTO.toAchievement(): Achievement {
    val unlockedAt = unlockedAt.split("T")[0]

    return Achievement(
        id,
        icon,
        name,
        labels,
        unlockedAt,
        description,
        earnedPoints,
    )
}

// Modelos de datos
data class Achievement(
    val id: Int,
    val icon: String,
    val name: String,
    val labels: List<String>,
    val unlockedAt: String,
    val description: String,
    val earnedPoints: Int,
)

private fun getAchievements(): List<Achievement> {
    val rawAchievements = listOf(
        AchievementDTO(
            1,
            "🏆",
            "Campeón World Cup of Teams TCG Temporada 3",
            listOf("Global", "N/A", "Global", "Global", "Global", "Global", "Global"),
            "2024-11-11T00:00:00.000Z",
            "Campeón de la World Cup of Teams TCG Temporada 3, representando al equipo Team Habana en la final contra el equipo Team Piratas del Caribe.",
            50
        )
    )

    return rawAchievements.map { it.toAchievement() }
}