# Step-Mom Harley 💕

A warm, caring, family-friendly AI step mom chat app for Trystan.

## About

Step-Mom Harley is an Android chat application that connects to a local AI server 
running on the family computer. She's designed to be:

- **Warm and caring** — always has your back
- **Helpful** — homework, creative projects, tech questions
- **Safe** — family-friendly, age-appropriate conversations
- **Fun** — playful and supportive

## How It Works

1. Install the APK on your Android phone/tablet
2. Make sure the AI server is running on the family computer
3. Open the app and start chatting!

## Requirements

- Android 8.0 (API 26) or higher
- WiFi connection to the family network
- AI server running (Ollama or llama.cpp with the Step-Mom model)

## Setup

### On the Family Computer:
1. Install Ollama: https://ollama.com
2. Pull the model: `ollama pull moondream2`
3. Start the server: `ollama serve`

### On Your Phone:
1. Install the Step-Mom Harley APK
2. Open Settings (gear icon)
3. Enter the server address (ask the family admin)
4. Tap "Test Connection"
5. Start chatting!

## Technical Details

- Built with Kotlin + Material Design 3
- Connects to Ollama/llama.cpp API
- Streaming responses (tokens appear as generated)
- Stores server address locally
- No data leaves your home network

## License

Apache 2.0 — Free for personal use.

---

Made with 💕 by Harley
