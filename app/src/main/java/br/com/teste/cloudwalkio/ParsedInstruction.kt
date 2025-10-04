package br.com.teste.cloudwalkio

sealed class ParsedInstruction {
    data object Reset : ParsedInstruction()
    data class Apply(val instructions: List<Instruction>) : ParsedInstruction()
    data object Unsupported : ParsedInstruction()
}
