package ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Tray
import tools.ShutdownManager
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter


object TrayLayout {
    @Composable
    fun createTray(scope: ApplicationScope) {
        scope.apply {
            Tray(
                icon = painterResource("images/compose-logo.png"),
                menu = {
                    Item(
                        "退出",
                        onClick = ::exitApplication
                    )
                    Item(
                        "1小时后关机",
                        onClick = {
                            ShutdownManager.shutdownAtTime(60 * 60)
                        }
                    )
                }
            )
        }

    }
}
