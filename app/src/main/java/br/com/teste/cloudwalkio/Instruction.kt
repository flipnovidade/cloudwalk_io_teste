package br.com.teste.cloudwalkio

sealed class Instruction {
    data class ChangeTitle(val title: String) : Instruction()
    data class ChangeBackground(val colorHex: String) : Instruction()
    data class ChangeTitleColor(val colorHex: String) : Instruction()
    data class ToggleAvatar(val show: Boolean) : Instruction()
    data class ChangeAvatarColor(val colorHex: String) : Instruction()
    data class SetCardStyle(val cardStyle: CardStyle) : Instruction()
    data class SetFormFields(val fields: List<String>) : Instruction()
}
