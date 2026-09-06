package com.harley.stepmom

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val serverUrlInput = findViewById<EditText>(R.id.serverUrlInput)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val testButton = findViewById<Button>(R.id.testButton)

        val prefs = getSharedPreferences("stepmom_harley", MODE_PRIVATE)
        serverUrlInput.setText(prefs.getString("server_url", "http://100.104.127.89:11434"))

        saveButton.setOnClickListener {
            val url = serverUrlInput.text.toString().trim()
            if (url.isNotEmpty()) {
                prefs.edit().putString("server_url", url).apply()
                Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        testButton.setOnClickListener {
            val url = serverUrlInput.text.toString().trim()
            val service = LlmService(url)
            testButton.isEnabled = false
            testButton.text = "Testing..."
            lifecycleScope.launch(Dispatchers.IO) {
                val ok = service.checkConnection()
                runOnUiThread {
                    testButton.isEnabled = true
                    testButton.text = "Test Connection"
                    Toast.makeText(
                        this@SettingsActivity,
                        if (ok) "Connected!" else "Can't reach server",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
