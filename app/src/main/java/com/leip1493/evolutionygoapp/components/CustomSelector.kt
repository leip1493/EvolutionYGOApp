package com.leip1493.evolutionygoapp.components

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSelector(
    selected: String,
    onSelectedChange: (String) -> Unit,
    modifier: Modifier,
    data: List<String>,
    placeholder: String
) {
//    var selected by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded, onExpandedChange = { expanded = it }, modifier = modifier) {
        OutlinedTextField(
            selected,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            maxLines = 1,
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White,

                ),
            placeholder = {
                Text(placeholder, color = Color.White)
            },
            modifier = Modifier.menuAnchor(),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }
        )
        ExposedDropdownMenu(
            expanded,
            onDismissRequest = { expanded = false },
        ) {
            data.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        expanded = false
                        onSelectedChange(it)
                    }
                )
            }
        }
    }
}
