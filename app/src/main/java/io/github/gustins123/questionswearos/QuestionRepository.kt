package io.github.gustins123.questionswearos

import android.content.Context
import kotlinx.serialization.json.Json
import java.io.IOException

object QuestionRepository {

    private var questions: List<String> = emptyList()

    // Loads questions from the asset file.
    // Should be called once, ideally from the TileService.
    fun loadQuestions(context: Context) {
        if (questions.isNotEmpty()) return // Don't load if already loaded

        try {
            val jsonString = context.assets.open("questions.json")
                .bufferedReader()
                .use { it.readText() }

            questions = Json.decodeFromString<List<String>>(jsonString)
        } catch (ioException: IOException) {
            // In a real app, you might want to log this error
            ioException.printStackTrace()
            questions = listOf("Error: Could not load questions.")
        }
    }

    // Returns a single random question from the loaded list.
    fun getRandomQuestion(): String {
        return questions.randomOrNull() ?: "No questions found. Tap to retry."
    }
}