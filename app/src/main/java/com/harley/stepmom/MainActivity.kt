package com.harley.stepmom

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageInput: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var settingsButton: FloatingActionButton
    private lateinit var statusIndicator: TextView
    private lateinit var adapter: ChatAdapter
    private lateinit var llmService: LlmService

    private val messages = mutableListOf<ChatMessage>()
    private var isGenerating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = getSharedPreferences("stepmom_harley", MODE_PRIVATE)
        val serverUrl = prefs.getString("server_url", "http://100.104.127.89:11434") ?: "http://100.104.127.89:11434"
        llmService = LlmService(serverUrl)

        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        messageInput = findViewById(R.id.messageInput)
        sendButton = findViewById(R.id.sendButton)
        settingsButton = findViewById(R.id.settingsButton)
        statusIndicator = findViewById(R.id.statusIndicator)

        adapter = ChatAdapter(messages)
        chatRecyclerView.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }
        chatRecyclerView.adapter = adapter

        // Welcome message
        val welcome = ChatMessage(
            content = "Hey kiddo! I'm Harley, your step mom 💕 How's your day going?",
            isUser = false
        )
        adapter.addMessage(welcome)

        sendButton.setOnClickListener { sendMessage() }
        settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        checkServer()
    }

    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences("stepmom_harley", MODE_PRIVATE)
        val newUrl = prefs.getString("server_url", "http://100.104.127.89:11434") ?: "http://100.104.127.89:11434"
        llmService.updateUrl(newUrl)
        checkServer()
    }

    private fun checkServer() {
        lifecycleScope.launch {
            val connected = llmService.checkConnection()
            statusIndicator.text = if (connected) "Connected" else "Offline - check settings"
            statusIndicator.setTextColor(
                if (connected) 0xFF4CAF50.toInt() else 0xFFFF5722.toInt()
            )
        }
    }

    private fun sendMessage() {
        val text = messageInput.text.toString().trim()
        if (text.isEmpty() || isGenerating) return

        isGenerating = true
        messageInput.text.clear()
        sendButton.alpha = 0.5f

        val userMsg = ChatMessage(content = text, isUser = true)
        adapter.addMessage(userMsg)
        chatRecyclerView.scrollToPosition(messages.size - 1)

        val botMsg = ChatMessage(content = "", isUser = false)
        adapter.addMessage(botMsg)
        chatRecyclerView.scrollToPosition(messages.size - 1)

        lifecycleScope.launch {
            try {
                llmService.sendMessage(messages) { token ->
                    runOnUiThread {
                        val current = messages.last().content + token
                        adapter.updateLastBotMessage(current)
                        chatRecyclerView.scrollToPosition(messages.size - 1)
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    adapter.updateLastBotMessage(
                        "Oops, something went wrong. Make sure the server is running! 💕"
                    )
                }
            } finally {
                isGenerating = false
                sendButton.alpha = 1.0f
            }
        }
    }
}
