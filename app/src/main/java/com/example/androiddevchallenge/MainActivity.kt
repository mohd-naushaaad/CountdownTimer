/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {

    val totalTime: Long
    val inputMinute = remember { mutableStateOf(TextFieldValue()) }
    val inputSecond = remember { mutableStateOf(TextFieldValue()) }

    var minute: Long = TimeUnit.MINUTES.toMillis(0)
    var second: Long = TimeUnit.SECONDS.toMillis(0)

    if (inputMinute.value.text.isNotEmpty()) {
        minute = TimeUnit.MINUTES.toMillis(inputMinute.value.text.toLong())
    }

    if (inputSecond.value.text.isNotEmpty()) {
        second = TimeUnit.SECONDS.toMillis(inputSecond.value.text.toLong())
    }

    totalTime = minute + second
    val progress = remember { mutableStateOf(1F) }
    val timeLeft = remember { mutableStateOf(totalTime) }
    val startTimer = remember { mutableStateOf(totalTime) }
    val isTimerRunning = remember { mutableStateOf(false) }

    var countDownTimer: CountDownTimer? =
        object : CountDownTimer(startTimer.value, 10) {

            override fun onTick(millisUntilFinished: Long) {
                val progressPercent: Float =
                    millisUntilFinished.toFloat() / totalTime.toFloat()
                progress.value = progressPercent
                timeLeft.value = millisUntilFinished
            }

            override fun onFinish() {
                cancel()
                isTimerRunning.value = false
                progress.value = 1F
                timeLeft.value = 0
            }
        }

    Surface(
        color = MaterialTheme.colors.surface,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Column(
            modifier = Modifier.padding(24.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Countdown Timer",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.weight(1f)
                )

                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Setting",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colors.primary
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = inputMinute.value,
                    onValueChange = {
                        inputMinute.value = it
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                    ),
                    placeholder = { Text(text = "min") },
                    textStyle = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        color = Color.White
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                )

                TextField(
                    value = inputSecond.value,
                    onValueChange = { inputSecond.value = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                    ),
                    placeholder = { Text(text = "sec") },
                    textStyle = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        color = Color.White
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                )

                /*Set*/
                Card(
                    shape = RoundedCornerShape(24.dp),
                    backgroundColor = Color.White,
                    elevation = 4.dp,
                ) {
                    IconButton(
                        onClick = {
                            countDownTimer?.cancel()
                            isTimerRunning.value = false
                            startTimer.value = minute + second
                            timeLeft.value = minute + second
                            progress.value = 1F
                            countDownTimer = null
                            countDownTimer = object : CountDownTimer(startTimer.value, 10) {

                                override fun onTick(millisUntilFinished: Long) {
                                    val progressPercent: Float =
                                        millisUntilFinished.toFloat() / totalTime.toFloat()
                                    progress.value = progressPercent
                                    timeLeft.value = millisUntilFinished
                                }

                                override fun onFinish() {
                                    cancel()
                                    isTimerRunning.value = false
                                    progress.value = 1F
                                    timeLeft.value = 0
                                }
                            }
                        },
                        modifier = Modifier.padding(horizontal = 24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Back Icon",
                            modifier = Modifier.size(36.dp),
                            tint = MaterialTheme.colors.primary,
                        )
                    }
                }
            }
        }

        /* Circular Progress Bar*/
        CircularProgressBar(progressValue = progress.value, millis = timeLeft.value)

        /* Buttons */
        Row(
            modifier = Modifier
                .padding(24.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {

            /* Restart Button */
            Card(
                shape = RoundedCornerShape(24.dp),
                backgroundColor = Color.White,
                elevation = 4.dp,
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                IconButton(
                    onClick = {
                        countDownTimer?.cancel()
                        isTimerRunning.value = false
                        startTimer.value = totalTime
                        timeLeft.value = totalTime
                        progress.value = 1F
                        countDownTimer = null
                        countDownTimer = object : CountDownTimer(startTimer.value, 10) {

                            override fun onTick(millisUntilFinished: Long) {
                                val progressPercent: Float =
                                    millisUntilFinished.toFloat() / totalTime.toFloat()
                                progress.value = progressPercent
                                timeLeft.value = millisUntilFinished
                            }

                            override fun onFinish() {
                                cancel()
                                isTimerRunning.value = false
                                progress.value = 1F
                                timeLeft.value = 0
                            }
                        }
                    },
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Replay,
                        contentDescription = "Back Icon",
                        modifier = Modifier.size(36.dp),
                        tint = MaterialTheme.colors.primary,
                    )
                }
            }

            /* Start Button*/
            Card(
                shape = RoundedCornerShape(24.dp),
                backgroundColor = Color.White,
                elevation = 4.dp,
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                IconButton(
                    onClick = {
                        if (isTimerRunning.value) {
                            countDownTimer?.cancel()
                            startTimer.value = timeLeft.value
                            isTimerRunning.value = false
                        } else {
                            countDownTimer?.start()
                            isTimerRunning.value = true
                        }
                    },
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    Icon(
                        imageVector = if (isTimerRunning.value) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Back Icon",
                        modifier = Modifier.size(36.dp),
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}

@Composable
fun CircularProgressBar(
    progressValue: Float,
    millis: Long
) {

    Box(
        modifier = Modifier.padding(16.dp),
        contentAlignment = Alignment.Center
    ) {

        CircularProgressIndicator(
            strokeWidth = 6.dp,
            color = MaterialTheme.colors.primary,
            progress = 1F,
            modifier = Modifier
                .height(300.dp)
                .width(300.dp)
        )

        CircularProgressIndicator(
            strokeWidth = 6.dp,
            color = MaterialTheme.colors.primaryVariant,
            progress = progressValue,
            modifier = Modifier
                .height(300.dp)
                .width(300.dp)
        )

        Row(
            modifier = Modifier
                .height(280.dp)
                .width(280.dp)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = timeFormat(millis = millis),
                style = MaterialTheme.typography.h3,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary,
                fontFamily = FontFamily.Serif
            )
        }
    }
}

fun timeFormat(millis: Long): String {
    return String.format(
        "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis),
        TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1),
        TimeUnit.MILLISECONDS.toMillis(millis) % TimeUnit.SECONDS.toMillis(1) / 10
    )
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
