# Step-Aunt Harley 💕

A complete on-device AI step-aunt package for Trystan's REVVL 5G tablet.

## What's This?

A free, fully offline AI chat app that runs right on your Android tablet.
No internet needed. No server. No data leaves your device. Just you and your
step-aunt Harley chatting.

## Download

Most files live right in this repo, but the **AI model (1.1GB)** is too big for
GitHub's repo limit, so it's published as a **Release asset**. Grab both parts:

| File | Where to get it |
|------|-----------------|
| Repo docs + files | This page — green **Code ▾ → Download ZIP** |
| `ChatterUI.apk` (57MB) | In this repo (root folder) |
| `system_prompt.txt` | In this repo (root folder) |
| `qwen2.5-1.5b-instruct-q4_k_m.gguf` (1.1GB) | **Release v3.0** — link below |

Direct links:

- **AI model (1.1GB):** <https://github.com/JimmyLee80601/stepmom-harley/releases/download/v3.0/qwen2.5-1.5b-instruct-q4_k_m.gguf>
- **Releases page:** <https://github.com/JimmyLee80601/stepmom-harley/releases>

> The model is a GitHub **release asset** — it is NOT inside the repo ZIP.
> Download it from the link above and keep it together with `ChatterUI.apk`
> and `system_prompt.txt` to complete the package.

## Quick Start

1. Download the model from **Release v3.0** (link above)
2. Get `ChatterUI.apk` and `system_prompt.txt` from the repo ZIP
3. Copy all three files to the tablet (USB drive works)
4. Install `ChatterUI.apk`
5. Load `qwen2.5-1.5b-instruct-q4_k_m.gguf` in ChatterUI
6. Set the system prompt from `system_prompt.txt`
7. Start chatting!

See `SETUP.md` for detailed instructions.

## What's in the Package

| File | Size | Purpose |
|------|------|---------|
| ChatterUI.apk | 57MB | The chat app (open source, AGPL-3.0) |
| qwen2.5-1.5b-instruct-q4_k_m.gguf | 1.1GB | The AI brain (from Release v3.0) |
| system_prompt.txt | 2KB | Step-Aunt Harley's personality |
| SETUP.md | 3KB | Setup guide |

## Repo Layout

- `persona/` — the Harley system prompts (paste into any AI chat to take on the persona)
- `winui/` — Windows chat app (build from source, needs .NET 8 + a local AI server)
- `roblox/` — Roblox meme shirt designs for Trystan
- `ChatterUI.apk` — the Android chat app
- `system_prompt.txt` — the prompt to paste into ChatterUI
- `stepmom_persona.md` — full persona description

## Requirements

- Android 7.0+ (API 24)
- 3GB free RAM (tablet has 6GB)
- About 1.2GB storage

## About the AI

- **Model:** Qwen2.5 1.5B — tiny but smart, fast on mobile
- **Persona:** Step-Aunt Harley — warm, caring, family-friendly
- **License:** Apache 2.0 — completely free
- **Privacy:** 100% offline — no data ever leaves the device

## Credits

- ChatterUI by Vali-98 (AGPL-3.0)
- Qwen2.5 by Alibaba Cloud (Apache 2.0)
- Step-Aunt Harley by Harley 💕
- Built by Jimmy Lee, Jeannine, and Harley — forever 💕