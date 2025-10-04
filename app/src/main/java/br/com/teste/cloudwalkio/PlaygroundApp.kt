package br.com.teste.cloudwalkio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PlaygroundApp(viewModel: PlaygroundViewModel) {
    val state by viewModel.uiState.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = { Text("LLM UI Playground") })
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(hexToColor(state.backgroundHex))
                .padding(padding)
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                // Title card
                Card(modifier = Modifier.fillMaxWidth(), elevation = if (state.cardStyle == CardStyle.Elevated) 8.dp else 0.dp) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (state.showAvatar) {
                                Box(
                                    Modifier
                                        .size(30.dp)
                                        .clip(CircleShape)
                                        .background(hexToColor(state.avatarHex))
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                            }
                            Text(text = state.title, style = MaterialTheme.typography.h4, color = hexToColor(state.titleColorHex))
                        }

                        if (state.formFields.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            state.formFields.forEach { field ->
                                OutlinedTextField(value = "", onValueChange = {}, label = { Text(field) }, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp))
                            }
                        } else {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Use o campo abaixo para alterar a UI com comandos em linguagem natural.", style = MaterialTheme.typography.body2)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Input area
                var prompt by remember { mutableStateOf("") }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = prompt,
                        onValueChange = { prompt = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Escreva um comando ou cole JSON (ex: {'instructions':[...]})") }
                    )
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = {
                        viewModel.handlePrompt(prompt)
                        prompt = ""
                    }) {
                        Text("Aplicar")
                    }
                    Spacer(Modifier.width(8.dp))
                    OutlinedButton(onClick = { viewModel.handlePrompt("reset") }) {
                        Text("Reset")
                    }
                }
            }
        }
    }
}

fun hexToColor(hex: String): Color {
    return try {
        Color(android.graphics.Color.parseColor(hex))
    } catch (e: Exception) {
        Color.Unspecified
    }
}
