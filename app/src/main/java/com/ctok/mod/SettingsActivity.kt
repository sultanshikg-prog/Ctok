package com.ctok.mod

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val ctx = LocalContext.current
            MaterialTheme(colorScheme = dynamicLightColorScheme(ctx)) { Screen() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen() {
    val prefs = LocalContext.current.getSharedPreferences("ctok", 0)
    var streak by remember { mutableStateOf(prefs.getBoolean("streak", false)) }
    var antiBurn by remember { mutableStateOf(prefs.getBoolean("antiBurn", false)) }
    var time by remember { mutableStateOf(prefs.getString("time", "12:00")!!) }
    var sticker by remember { mutableStateOf(prefs.getString("sticker", "🔥")!!) }

    Scaffold(topBar = { TopAppBar(title = { Text("Настройки Ctok") }) }) { pad ->
        Column(Modifier.padding(pad).padding(16.dp)) {
            ListItem(
                headlineContent = { Text("Автопродление огонька") },
                trailingContent = {
                    Switch(streak, { streak = it; prefs.edit().putBoolean("streak", it).apply() })
                })
            if (streak) {
                OutlinedTextField(time, { time = it; prefs.edit().putString("time", it).apply() },
                    label = { Text("Время (ЧЧ:ММ)") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(sticker, { sticker = it; prefs.edit().putString("sticker", it).apply() },
                    label = { Text("Стикер") }, modifier = Modifier.fillMaxWidth())
            }
            ListItem(
                headlineContent = { Text("Анти-выгорание экрана") },
                supportingContent = { Text("Скрыть иконки в ленте") },
                trailingContent = {
                    Switch(antiBurn, { antiBurn = it; prefs.edit().putBoolean("antiBurn", it).apply() })
                })
        }
    }
}
