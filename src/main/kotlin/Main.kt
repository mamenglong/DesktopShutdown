import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import tools.ShutdownManager
import ui.TrayLayout
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        var timeMills by remember { mutableStateOf(10L) }
        var showTimePicker by remember { mutableStateOf(false) }
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "定时关机",
                    style = MaterialTheme.typography.h3
                )
                Column(modifier = Modifier.wrapContentHeight()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.wrapContentHeight().fillMaxWidth()
                    ) {
                        Text(
                            "时间",
                            modifier = Modifier.wrapContentHeight()
                                .padding(horizontal = 5.dp)
                                .onClick {
                                    showTimePicker = true
                                    //localTimeStr = localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                                },
                            textAlign = TextAlign.Center
                        )
                        OutlinedTextField(
                            label = {
                                Text("多少分之后关机")
                            },
                            trailingIcon = {
                                Text("分之后关机")
                            },
                            value = timeMills.toString(),
                            modifier = Modifier
                                .wrapContentHeight()
                                .widthIn(100.dp, 300.dp)
                                .padding(horizontal = 5.dp),
                            onValueChange = {
                                timeMills = it.toLongOrNull() ?: 10L
                            })
                        Button(
                            modifier = Modifier
                                .padding(horizontal = 5.dp),
                            onClick = {
                                ShutdownManager.shutdownAtTime(timeMills * 60)
                            }) {
                            Text("设置")
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            modifier = Modifier.padding(5.dp),
                            onClick = {
                                ShutdownManager.shutdownAtTime(10 * 60)
                            }) {
                            Text("10分钟后")
                        }
                        Button(
                            modifier = Modifier.padding(5.dp),
                            onClick = {
                                ShutdownManager.shutdownAtTime(30 * 60)
                            }) {
                            Text("半小时后")
                        }
                        Button(
                            modifier = Modifier.padding(5.dp),
                            onClick = {
                                ShutdownManager.shutdownAtTime(60 * 60)
                            }) {
                            Text("1小时后")
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(onClick = {
                            ShutdownManager.cancelShutdown()
                        }) {
                            Text("取消关机计划")
                        }
                    }
                }
            }

        }
    }
}

fun main() = application {
    TrayLayout.createTray(this)
    val defaultWindowSize = DpSize(500.dp, 400.dp)
    val windowState = rememberWindowState(
        placement = WindowPlacement.Floating,
        size = defaultWindowSize
    )
    Window(
        state = windowState,
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}
