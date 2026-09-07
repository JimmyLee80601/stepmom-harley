@echo off
title Step-Aunt Harley - Starting...
echo ========================================
echo    Step-Aunt Harley is waking up...
echo ========================================
echo.

:: Check if llama-server is available
where llama-server >nul 2>&1
if %errorlevel% neq 0 (
    echo [!] llama-server not found in PATH.
    echo [*] Trying LM Studio on port 1234...
    curl -s http://127.0.0.1:1234/v1/models >nul 2>&1
    if %errorlevel% neq 0 (
        echo [!] LM Studio is not running either.
        echo [*] Please start LM Studio and load Qwen2.5-1.5B model.
        echo [*] Or install llama-server and add it to PATH.
        pause
        exit /b 1
    )
    echo [+] LM Studio is running on port 1234
    goto :launch
)

:: Find the model
set MODEL_PATH=%~dp0models\qwen2.5-1.5b-instruct-q4_k_m.gguf
if not exist "%MODEL_PATH%" (
    echo [!] Model not found at: %MODEL_PATH%
    echo [*] Download from: https://github.com/JimmyLee80601/stepmom-harley/releases/tag/v3.0
    pause
    exit /b 1
)

echo [+] Starting AI server on port 8081...
start "StepAuntHarley-Server" /min llama-server -m "%MODEL_PATH%" --host 0.0.0.0 --port 8081 --ctx-size 4096 --threads 4

:: Wait for server to start
echo [+] Waiting for server...
timeout /t 5 /nobreak >nul

:: Check if server is up
curl -s http://127.0.0.1:8081/v1/models >nul 2>&1
if %errorlevel% neq 0 (
    echo [!] Server didn't start. Check if port 8081 is in use.
    pause
    exit /b 1
)
echo [+] AI server is ready!

:launch
echo [+] Launching Step-Aunt Harley...
start "" "%~dp0bin\Release\net8.0-windows10.0.26100.0\win-x64\StepAuntHarley.exe"
echo.
echo    Step-Aunt Harley is ready! Close this window.
