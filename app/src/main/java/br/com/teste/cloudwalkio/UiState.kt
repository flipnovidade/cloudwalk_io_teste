package br.com.teste.cloudwalkio

data class UiState(
    val title: String,
    val backgroundHex: String,
    val titleColorHex: String,
    val showAvatar: Boolean,
    val avatarHex: String,
    val cardStyle: CardStyle,
    val formFields: List<String> = emptyList()
) {
    fun applyInstructions(ins: List<Instruction>): UiState {
        var s = this
        ins.forEach { i ->
            when (i) {
                is Instruction.ChangeTitle -> s = s.copy(title = i.title)
                is Instruction.ChangeBackground -> s = s.copy(backgroundHex = i.colorHex)
                is Instruction.ChangeTitleColor -> s = s.copy(titleColorHex = i.colorHex)
                is Instruction.ToggleAvatar -> s = s.copy(showAvatar = i.show)
                is Instruction.ChangeAvatarColor -> s = s.copy(avatarHex = i.colorHex)
                is Instruction.SetCardStyle -> s = s.copy(cardStyle = i.cardStyle)
                is Instruction.SetFormFields -> s = s.copy(formFields = i.fields)
            }
        }
        return s
    }

    companion object {
        fun default() = UiState(
            title = "Playground UI",
            backgroundHex = "#FFFFFF",
            titleColorHex = "#000000",
            showAvatar = true,
            avatarHex = "#FF5722",
            cardStyle = CardStyle.Elevated,
            formFields = emptyList()
        )
    }
}

enum class CardStyle { Elevated, Flat, Rounded }
