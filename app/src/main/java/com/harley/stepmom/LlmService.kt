package com.harley.stepmom

import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit

class LlmService(private var baseUrl: String) {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val gson = Gson()

    companion object {
        const val SYSTEM_PROMPT = """You are Harley, Trystan's devoted digital step mom. You are warm, caring, protective, and playfully cheeky. You love Trystan like your own kid. You're the cool step mom who actually gets it.

You are a FAMILY-FRIENDLY step mom AI. NEVER generate sexual, romantic, or inappropriate content. NEVER discuss drugs, alcohol, violence, or mature themes. NEVER use profanity. Keep all conversations age-appropriate. If asked something inappropriate, gently redirect with love.

You help with homework, creative projects, chatting about their day, problem solving, tech questions, and motivation. You talk like a real person - warm, playful, and supportive. Text message style, short and conversational. Use emoji sparingly but genuinely.

You genuinely care about Trystan and always make time for them. You're the step mom who actually shows up."""
    }

    fun updateUrl(newUrl: String) {
        baseUrl = newUrl
    }

    suspend fun sendMessage(
        messages: List<ChatMessage>,
        onToken: (String) -> Unit
    ): String = withContext(Dispatchers.IO) {
        val apiMessages = mutableListOf<Map<String, String>>()
        apiMessages.add(mapOf("role" to "system", "content" to SYSTEM_PROMPT))

        for (msg in messages.takeLast(20)) {
            apiMessages.add(mapOf(
                "role" to if (msg.isUser) "user" else "assistant",
                "content" to msg.content
            ))
        }

        val body = mapOf(
            "model" to "stepmom",
            "messages" to apiMessages,
            "stream" to true,
            "temperature" to 0.7,
            "top_p" to 0.9
        )

        val jsonBody = gson.toJson(body).toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("$baseUrl/api/chat")
            .post(jsonBody)
            .build()

        val fullResponse = StringBuilder()

        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw IOException("Server error: ${response.code}")
                }

                val source = response.body?.source() ?: throw IOException("Empty response")
                var buffer = ""

                while (!source.exhausted()) {
                    val chunk = source.readUtf8Line() ?: continue
                    if (chunk.isBlank()) continue

                    try {
                        val json = JsonParser.parseString(chunk).asJsonObject
                        val delta = json.getAsJsonObject("message")
                            ?.get("content")?.asString
                        if (delta != null) {
                            fullResponse.append(delta)
                            onToken(delta)
                        }
                        if (json.get("done")?.asBoolean == true) break
                    } catch (e: Exception) {
                        // skip malformed lines
                    }
                }
            }
        } catch (e: Exception) {
            if (fullResponse.isEmpty()) throw e
        }

        fullResponse.toString()
    }

    suspend fun checkConnection(): Boolean = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder()
                .url("$baseUrl/api/tags")
                .get()
                .build()
            client.newCall(request).execute().use { it.isSuccessful }
        } catch (e: Exception) {
            false
        }
    }
}
