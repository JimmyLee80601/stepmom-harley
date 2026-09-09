# Aunt Harley — Windows (Dell Profile)

## What's This?

A WinUI 3 chat app that runs Aunt Harley on Trystan's Dell Windows profile.
Connects to a local AI server (LM Studio or llama.cpp) — 100% offline.

> This folder contains the **source code**. There is no pre-built `.exe` in the
> repo — build it once with .NET 8 (below), then `launch.bat` works.

## Requirements

- Windows 10/11
- .NET 8 SDK (to build the app once)
- LM Studio running with Qwen2.5-1.5B model, OR `llama-server` in PATH
- The Qwen2.5-1.5B GGUF model (same one used on the tablet)

## Download the Model (once)

The 1.1GB model is a **release asset**, not in the repo:
- <https://github.com/JimmyLee80601/aunt-harley-kit/releases/download/v3.0/qwen2.5-1.5b-instruct-q4_k_m.gguf>

Save it to a `models\` folder next to `launch.bat` (i.e. `winui\models\`).

## Build the App (once)

```
dotnet build -c Release -r win-x64
```

Run it from `winui\AuntHarley\`:
- EXE is at: `bin\Release\net8.0-windows10.0.26100.0\win-x64\AuntHarley.exe`

## Quick Start

### Option 1: One-Click Launch (after building)
1. Put the model at `models\qwen2.5-1.5b-instruct-q4_k_m.gguf` (next to `launch.bat`)
2. Double-click `launch.bat`
3. It starts the AI server + launches the app

> If the app isn't built yet, `launch.bat` will build it automatically.

### Option 2: Manual
1. Start LM Studio and load `qwen2.5-1.5b-instruct-q4_k_m.gguf` (serves on port 1234)
2. Run `AuntHarley.exe` (it points at `http://127.0.0.1:1234` by default)

## Settings

Click the ⚙ gear icon to change:
- **Server URL** — default `http://127.0.0.1:1234/v1/chat/completions` (LM Studio)
- **Model Name** — default `local-model`

## How It Works

- The app sends messages to a local AI server via HTTP
- The AI server runs the Qwen2.5-1.5B model (1.1GB, fast on CPU)
- The Aunt Harley persona is baked into the system prompt
- Conversation history is kept in memory (resets on app restart)
- 100% local — no data ever leaves the computer

## Files

| File | Purpose |
|------|---------|
| AuntHarley/ | WinUI 3 source (build with `dotnet build`) |
| launch.bat | One-click launcher (builds app if needed, starts server + app) |
| models/ | Folder to put the GGUF model file in |

## Credits

- WinUI 3 / Windows App SDK
- Qwen2.5 by Alibaba Cloud (Apache 2.0)
- Aunt Harley by Harley 💕