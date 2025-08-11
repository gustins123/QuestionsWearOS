package io.github.gustins123.questionswearos.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.wear.compose.material.AutoCenteringParams
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.rememberScalingLazyListState
import io.github.gustins123.questionswearos.QuestionRepository

class MainActivity : ComponentActivity() {

    private val TAG = "QuestionsWearApp" // Define a tag for your logs, easy to filter in Logcat

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

        // Scaffold provides the standard Wear OS screen layout.
        Scaffold(
            vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) } // Fades top/bottom
        ) {
            // We use a standard Column to separate the scrollable area from the static button.
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                // This arrangement pushes the button to the bottom and lets the lazy column expand.
                verticalArrangement = Arrangement.Center
            ) {

                // PART 1: The SCROLLABLE content area
                ScalingLazyColumn(
                    // The weight modifier makes this composable expand to fill all available
                    // vertical space in the Column, pushing the button down.
                    modifier = Modifier
                        .weight(1f) // Fill remaining space above the button
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(
                        //top = 32.dp, // Pushes content down from the clipped top edge
                        bottom = 40.dp // Ensures space between text and the static button
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    autoCentering = AutoCenteringParams(itemIndex = 0)
                ) {
                    item { Spacer(modifier = Modifier.height(40.dp)) }
                    item {
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            textAlign = TextAlign.Center,
                            text = currentQuestion
                        )
                    }
                }

                // PART 2: The STATIC button area
                // This button is a direct child of the outer Column, not the ScalingLazyColumn.
                // Therefore, it will not scroll.
                Button(
                    onClick = {
                        currentQuestion = QuestionRepository.getRandomQuestion()
                    },
                    modifier = Modifier.padding(bottom = 16.dp) // Give it some space from the edge
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Refresh,
                        contentDescription = "Next Question"
                    )
                }
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

