package com.example.task7 // IMPORANT: Change this to your actual package name (e.g., com.example.task6)

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 1. DATA MODEL
// We use a data class to store the ID and Value of each counter
data class CounterItem(
    val id: Int,
    val value: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MultiCounterApp()
                }
            }
        }
    }
}

@Composable
fun MultiCounterApp() {
    // 2. STATE
    // mutableStateListOf is required for LazyColumn to update when we add/remove items
    val counters = remember { mutableStateListOf<CounterItem>() }
    
    // We track the ID separate from the list size to ensure names like "Counter_5" stay unique
    var nextId by remember { mutableIntStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // --- Header Section ---
        Text(
            text = "Task 7: Lazy Counters",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // --- Controls (Add / Clear) ---
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    // Add a new counter with the current 'nextId'
                    counters.add(CounterItem(id = nextId, value = 0))
                    nextId++ 
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Counter")
            }

            Button(
                onClick = { counters.clear() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(Icons.Default.Delete, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Clear All")
            }
        }

        Divider()

        // --- LAZY COLUMN (The Scrollable List) ---
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // items() allows us to loop through the 'counters' list
            items(items = counters, key = { it.id }) { item ->
                
                CounterRow(
                    item = item,
                    onDelete = {
                        counters.remove(item)
                    },
                    onValueChange = { newValue ->
                        // To update a specific item, we find its index and replace it
                        val index = counters.indexOf(item)
                        if (index != -1) {
                            counters[index] = item.copy(value = newValue)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CounterRow(
    item: CounterItem, 
    onDelete: () -> Unit, 
    onValueChange: (Int) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Counter Name
            Text(
                text = "Counter_${item.id}",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            // Buttons + and -
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilledIconButton(onClick = { onValueChange(item.value - 1) }) {
                    Text("-", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }

                Text(
                    text = "${item.value}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                FilledIconButton(onClick = { onValueChange(item.value + 1) }) {
                    Text("+", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }
            
            // Delete Icon
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Remove", tint = Color.Gray)
            }
        }
    }
}
