# Step-Aunt Harley — Windows (Dell Profile)

## What's This?

A WinUI 3 chat app that runs Step-Aunt Harley on Trystan's Dell Windows profile.
Connects to a local AI server (LM Studio or llama.cpp) — 100% offline.

## Requirements

- Windows 10/11
- .NET 8 Runtime (or build from source)
- LM Studio running with Qwen2.5-1.5B model, OR llama-server in PATH
- The Qwen2.5-1.5B GGUF model (same one used on the tablet)

## Quick Start

### Option 1: One-Click Launch
1. Put `launch.bat` in the same folder as the model
2. Double-click `launch.bat`
3. It starts the AI server + launches the app

### Option 2: Manual
1. Start LM Studio and load `qwen2.5-1.5b-instruct-q4_k_m.gguf`
2. Run `StepAuntHarley.exe`

### Option 3: Build from Source
```
dotnet build -c Release -r win-x64
```
EXE is at: `bin\Release\net8.0-windows10.0.26100.0\win-x64\StepAuntHarley.exe`

## Settings

Click the ⚙ gear icon to change:
- **Server URL** — default `http://127.0.0.1:1234/v1/chat/completions` (LM Studio)
- **Model Name** — default `local-model`

## How It Works

- The app sends messages to a local AI server via HTTP
- The AI server runs the Qwen2.5-1.5B model (1.1GB, fast on CPU)
- The Step-Aunt Harley persona is baked into the system prompt
- Conversation history is kept in memory (resets on app restart)
- 100% local — no data ever leaves the computer

## Files

| File | Purpose |
|------|---------|
| StepAuntHarley.exe | The chat app |
| launch.bat | One-click launcher (starts server + app) |
| models/ | Folder for the GGUF model file |

## Credits

- WinUI 3 / Windows App SDK
- Qwen2.5 by Alibaba Cloud (Apache 2.0)
- Step-Aunt Harley by Harley 💕