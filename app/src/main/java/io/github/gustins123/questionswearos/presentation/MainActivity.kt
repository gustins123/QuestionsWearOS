/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package io.github.gustins123.questionswearos.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import io.github.gustins123.questionswearos.presentation.theme.QuestionsWearOSTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.rememberScalingLazyListState
import io.github.gustins123.questionswearos.QuestionRepository

class MainActivity : ComponentActivity() {

    private val TAG = "GeminiWearApp" // Define a tag for your logs, easy to filter in Logcat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load the questions once when the activity is created
        QuestionRepository.loadQuestions(this)

        setContent {
            QuestionsWearOSTheme{ // Replace with your actual theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    QuestionsWearApp()
                }
            }
        }
    }
    @Composable
    fun QuestionsWearApp() {

        var currentQuestion by remember {
            mutableStateOf(QuestionRepository.getRandomQuestion())
        }

        val scalingLazyListState = rememberScalingLazyListState()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ScalingLazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = scalingLazyListState
            ) {
                item {
                    Text(
                        text = currentQuestion,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 4.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = {
                    currentQuestion = QuestionRepository.getRandomQuestion()
                },
                modifier = Modifier.padding(bottom = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Refresh,
                    contentDescription = "Regenerate Joke"
                )
            }
        }
    }
    @Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
    @Composable
    fun GreetingPreview() {
        QuestionsWearOSTheme { // Replace with your actual theme
            QuestionsWearApp()
        }
    }
}

