package br.com.teste.cloudwalkio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaygroundViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState.default())
    val uiState: StateFlow<UiState> = _uiState

    private val parser = JsonPromptParser() // parser que lê JSON-instruções (simulado)

    fun handlePrompt(raw: String) {
        viewModelScope.launch {
            val instr = parser.parse(raw.trim())
            when (instr) {
                is ParsedInstruction.Reset -> _uiState.value = UiState.default()
                is ParsedInstruction.Apply -> {
                    val newState = _uiState.value.applyInstructions(instr.instructions)
                    _uiState.value = newState
                }
                is ParsedInstruction.Unsupported -> {
                    // aqui poderia expor um StateFlow de mensagens para a UI
                }
            }
        }
    }
}
