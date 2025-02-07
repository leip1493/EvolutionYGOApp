package com.leip1493.evolutionygoapp.home.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leip1493.evolutionygoapp.R
import com.leip1493.evolutionygoapp.ui.theme.Background

@Composable
fun HomeScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = Background
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
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
//                    OutlinedTextField(
//                        "Season 4",
//                        onValueChange = {},
//                        modifier = Modifier.weight(1f),
//                        colors = OutlinedTextFieldDefaults.colors(
//                            unfocusedTextColor = Color.White,
//                            focusedTextColor = Color.White,
//                        )
//                    )
//                    OutlinedTextField(
//                        "Global",
//                        onValueChange = {},
//                        modifier = Modifier.weight(1f),
//                        colors = OutlinedTextFieldDefaults.colors(
//                            unfocusedTextColor = Color.White,
//                            focusedTextColor = Color.White,
//
//                        )
//                    )
                }

                val players = listOf("Akane", "TheGhost9103", "Ene", "Hantengu")

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
//                    contentPadding = PaddingValues(16.dp), // Margen exterior al listado
                    verticalArrangement = Arrangement.spacedBy(16.dp), // Espaciado vertical entre elementos
                    horizontalArrangement = Arrangement.spacedBy(16.dp), // Espaciado horizontal entre elementos
                    content = {
                        itemsIndexed(players) { index, player ->
                            TopPlayerCard(player, index + 1)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun TopPlayerCard(playerName: String, position: Int) {
    val initials = getPlayerInitials(playerName)
    
    Card(
        border = BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .height(250.dp)
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
                "#$position $playerName",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,

                )
            Text(
                "Points: 999",
                color = Color.White,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp
            )
            Text(
                "Wins: 999",
                color = Color.Green,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp
            )
            Text(
                "Losses: 0",
                color = Color.Red,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp
            )
            Text(
                "Win Rate: 75.66%",
                color = Color.White,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp
            )

        }
    }
}

@Composable
private fun getPlayerInitials(playerName: String): String {
    val splittedName = playerName.split(" ")
    val firstName = splittedName[0]
    val lastName = splittedName.getOrNull(1) ?: ""
    val initials =
        "${firstName.first()}${if (lastName.isNotEmpty()) lastName else firstName.slice(1..1)}".uppercase()
    return initials
}