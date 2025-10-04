package br.com.teste.cloudwalkio

import org.json.JSONObject

/**
 * JsonPromptParser: tenta interpretar a entrada do usuário como
 * 1) um prompt especial (reset)
 * 2) ou um JSON com instruções no formato:
 * {
 *   "instructions": [
 *     {"type":"changeBackground","color":"#2196F3"},
 *     {"type":"changeTitle","title":"Olá"}
 *   ]
 * }
 *
 * Se a entrada não for JSON, tenta reconhecer prompts simples em português.
 */
class JsonPromptParser {

    fun parse(input: String): ParsedInstruction {
        if (input.isBlank()) return ParsedInstruction.Unsupported
        val trimmed = input.trim()
        if (trimmed.equals("reset", ignoreCase = true) || trimmed.equals("reiniciar", ignoreCase = true)) {
            return ParsedInstruction.Reset
        }

        // tenta parsear JSON
        return try {
            val obj = JSONObject(trimmed)
            val arr = obj.optJSONArray("instructions") ?: return ParsedInstruction.Unsupported
            val list = mutableListOf<Instruction>()
            for (i in 0 until arr.length()) {
                val it = arr.getJSONObject(i)
                when (it.optString("type")) {
                    "changeBackground" -> list.add(Instruction.ChangeBackground(it.optString("color", "#FFFFFF")))
                    "changeTitle" -> list.add(Instruction.ChangeTitle(it.optString("title", "Playground UI")))
                    "changeTitleColor" -> list.add(Instruction.ChangeTitleColor(it.optString("color", "#000000")))
                    "toggleAvatar" -> list.add(Instruction.ToggleAvatar(it.optBoolean("show", true)))
                    "changeAvatarColor" -> list.add(Instruction.ChangeAvatarColor(it.optString("color", "#FF5722")))
                    "setCardStyle" -> {
                        val s = when (it.optString("style")) {
                            "Flat" -> CardStyle.Flat
                            "Rounded" -> CardStyle.Rounded
                            else -> CardStyle.Elevated
                        }
                        list.add(Instruction.SetCardStyle(s))
                    }
                    "setFormFields" -> {
                        val fields = mutableListOf<String>()
                        val farr = it.optJSONArray("fields")
                        if (farr != null) {
                            for (j in 0 until farr.length()) fields.add(farr.getString(j))
                        }
                        list.add(Instruction.SetFormFields(fields))
                    }
                }
            }
            if (list.isEmpty()) ParsedInstruction.Unsupported else ParsedInstruction.Apply(list)
        } catch (e: Exception) {
            // fallback: reconhece alguns prompts simples em pt-br
            simpleMapping(trimmed)
        }
    }

    private fun simpleMapping(input: String): ParsedInstruction {
        val s = input.lowercase()
        return when {
            s.contains("fundo azul") -> ParsedInstruction.Apply(listOf(Instruction.ChangeBackground("#2196F3"), Instruction.ChangeTitleColor("#FFFFFF")))
            s.contains("fundo verde") -> ParsedInstruction.Apply(listOf(Instruction.ChangeBackground("#4CAF50"), Instruction.ChangeTitleColor("#FFFFFF")))
            s.contains("perfil") && s.contains("cartao") -> ParsedInstruction.Apply(listOf(
                Instruction.ChangeTitle("Perfil de Usuário"),
                Instruction.SetCardStyle(CardStyle.Rounded),
                Instruction.ToggleAvatar(true)
            ))
            s.contains("avatar vermelho") -> ParsedInstruction.Apply(listOf(
                Instruction.ToggleAvatar(true),
                Instruction.ChangeAvatarColor("#D32F2F")
            ))
            s.contains("formulario") || s.contains("formulário") -> ParsedInstruction.Apply(listOf(
                Instruction.SetFormFields(listOf("Nome", "Email", "Telefone")),
                Instruction.ChangeTitle("Formulário")
            ))
            else -> ParsedInstruction.Unsupported
        }
    }
}
