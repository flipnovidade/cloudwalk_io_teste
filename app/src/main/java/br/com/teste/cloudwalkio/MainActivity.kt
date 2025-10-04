package br.com.teste.cloudwalkio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.teste.cloudwalkio.theme.UiPlaygroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UiPlaygroundTheme {
                val vm: PlaygroundViewModel = viewModel()
                PlaygroundApp(viewModel = vm)
            }
        }
    }
}
