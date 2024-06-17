package com.example.foodnutrition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodnutrition.ui.theme.FoodNutritionTheme
import com.example.foodnutrition.Facts

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodNutritionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FoodSelectionScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodSelectionScreen() {
    val facts = Facts()
    val foods = facts.foodsNutritionRecord

    var selectedFood by remember { mutableStateOf(foods[0]) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                readOnly = true,
                value = selectedFood.name,
                onValueChange = { },
                label = { Text("Select Food") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                modifier = Modifier
                    .menuAnchor()  // To align the dropdown menu with the TextField
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }  // Prevents keyboard from appearing
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                foods.forEach { food ->
                    DropdownMenuItem(
                        text = { Text(text = food.name) },
                        onClick = {
                            selectedFood = food
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Nutritional Information:")
        Text("Fat: ${selectedFood.fat} g")
        Text("Carbs: ${selectedFood.carbs} g")
        Text("Protein: ${selectedFood.protein} g")
    }
}

@Preview(showBackground = true)
@Composable
fun FoodSelectionScreenPreview() {
    FoodNutritionTheme {
        FoodSelectionScreen()
    }
}
