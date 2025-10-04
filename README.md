# UI Playground - Android (Jetpack Compose)

Projeto para um playground que aplica instruções de UI a partir de prompts em linguagem natural em pt
ou de um JSON de instruções (simulando a saída de um LLM).

## Como usar

- Importe o projeto no Android Studio (*Import project (Gradle, Eclipse, etc.)*)
- Execute o app em um emulador ou device.

## Exemplos de prompts simples (pt-br)
- `fundo azul`
- `perfil em cartão`
- `mostre avatar vermelho`
- `formulário`
- `reset`

## Exemplos de payload JSON (cole no campo de prompt e aperte Aplicar)
```
{
  "instructions": [
    {"type":"changeBackground","color":"#2196F3"},
    {"type":"changeTitle","title":"Texto Aqui"},
    {"type":"changeTitleColor","color":"#753F31"},
    {"type":"toggleAvatar","show":false},
    {"type":"changeAvatarColor","color":"#D32F2F"},
    {"type":"setCardStyle","style":"Rounded"},
    {"type":"setFormFields","fields":["Nome:","Email:","Telefone:"]}
  ]
}
```

## Observações
- O parser aceita tanto JSON quanto prompts simples em português.
- Para integrar com um LLM real, troque `JsonPromptParser` por uma implementação que chame a API e retorne o JSON no formato esperado.

________________________________

## UI Playground - Android (Jetpack Compose)

Project for a playground that applies UI instructions from natural language prompts in Portuguese
or from a JSON set of instructions (simulating the output of an LLM).

# How to use

- Import the project into Android Studio (Import project (Gradle, Eclipse, etc.))
- Run the app on an emulator or device.

# Simple prompt examples (pt-br)

- `fundo azul`
- `perfil em cartão`
- `mostre avatar vermelho`
- `formulário`
- `reset`

# Example JSON payload (paste into the prompt field and press Apply)
```
{
  "instructions": [
    {"type":"changeBackground","color":"#2196F3"},
    {"type":"changeTitle","title":"Texto Aqui"},
    {"type":"changeTitleColor","color":"#753F31"},
    {"type":"toggleAvatar","show":false},
    {"type":"changeAvatarColor","color":"#D32F2F"},
    {"type":"setCardStyle","style":"Rounded"},
    {"type":"setFormFields","fields":["Nome:","Email:","Telefone:"]}
  ]
}
```

# Notes
- The parser accepts both JSON and simple prompts in Portuguese.
- To integrate with a real LLM, replace `JsonPromptParser` with an implementation that calls the API and returns JSON in the expected format.