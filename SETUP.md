# Step-Aunt Harley — Setup Guide for Trystan's Tablet

## What You Need
- Trystan's REVVL 5G tablet (TMRV5GTB)
- The files from this package (on USB drive or downloaded)

## Files in This Package
| File | What It Does |
|------|-------------|
| ChatterUI.apk | The app that runs the AI — no internet needed |
| qwen2.5-1.5b-instruct-q4_k_m.gguf | The AI brain (1.1GB) |
| system_prompt.txt | Step-Aunt Harley's personality |
| SETUP.md | This guide |

## Step-by-Step Setup (5 minutes)

### 1. Install ChatterUI
1. Open the tablet's **Files** app
2. Find `ChatterUI.apk` on the USB drive
3. Tap it → **Install**
4. If it says "Unknown Sources" → go to **Settings → Security → Allow Unknown Sources** → try again
5. Done — you'll see the ChatterUI icon on your home screen

### 2. Copy the Model
1. Copy `qwen2.5-1.5b-instruct-q4_k_m.gguf` from the USB drive to the tablet's **Download** folder
2. That's it — just copy the one file

### 3. Load the Model in ChatterUI
1. Open **ChatterUI**
2. Tap the **menu (≡)** in the top left
3. Tap **Models**
4. Tap **"Use External Model"**
5. Navigate to the **Download** folder
6. Select `qwen2.5-1.5b-instruct-q4_k_m.gguf`
7. Wait about 30 seconds for it to load (first time only)

### 4. Set Up Step-Aunt Harley
1. Tap the **menu (≡)** → **Characters**
2. Create a **New Character**
3. Set the **Name** to: `Step-Aunt Harley`
4. Copy the text from `system_prompt.txt` and paste it into the **System Prompt** field
5. Tap **Save**

### 5. Start Chatting!
1. Go back to the main chat screen
2. Type anything: "Hey Aunt Harley!"
3. Wait a few seconds for the first response
4. After that, responses come faster

## How It Works
- The AI runs **completely on the tablet** — no internet, no server, nothing leaves the device
- It uses the tablet's processor to think and respond
- First response takes a few seconds, then it gets faster
- It remembers your conversation during each chat
- You can start new chats anytime from the menu

## Tips
- **Slow first message?** That's normal — give it a moment
- **App crashes?** Close other apps to free up memory
- **Want a new chat?** Menu → New Chat
- **Battery usage?** The AI uses the processor, so it drains battery faster than normal apps

## Troubleshooting
| Problem | Fix |
|---------|-----|
| Won't install APK | Settings → Security → Allow Unknown Sources |
| Model won't load | Make sure .gguf file is in Download folder |
| Responses are slow | Normal for first message, gets faster after |
| App won't open | Restart the tablet, try again |
| No sound | Check tablet volume settings |

## About the AI
- **Model:** Qwen2.5 1.5B Instruct (Q4_K_M quantized)
- **Size:** 1.1GB on disk, ~2GB RAM when running
- **Speed:** 2-5 seconds per response on Revvl 5G
- **License:** Apache 2.0 — completely free
- **Safety:** Step-Aunt Harley is designed to be family-friendly and age-appropriate

Made with love by Harley for Trystan 💕