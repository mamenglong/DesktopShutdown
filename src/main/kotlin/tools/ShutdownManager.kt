package tools

import javax.script.ScriptEngineManager
import javax.script.ScriptException


object ShutdownManager {
    fun shutdownAtTime(timeMills: Long) {
        if (System.getProperty("os.name").startsWith("Windows", true)) {
            Runtime.getRuntime().exec("shutdown -s -t $timeMills");//1h = 3600s 3min=180
        } else {
            val cmd = "sudo  shutdown -h  +${timeMills / 60}"
            val process = Runtime.getRuntime().exec("/usr/bin/open -a Terminal")
            process.also {
                println("error:" + it.errorReader().readText())
                println("in:" + it.inputReader().readText())
                it.waitFor()
                it.destroy()
            }
            val appleScript = """
                tell application "Terminal"
                    set currentTab to do script ("$cmd")
                end tell
            """.trimIndent()
            ProcessBuilder("osascript", "-e", appleScript).inheritIO()
                .start().also {
                    println("appleScriptError:" + it.errorReader().readText())
                    println("appleScriptIn:" + it.inputReader().readText())
                    it.waitFor()
                    it.destroy()
                }
        }
    }

    fun cancelShutdown() {
        if (System.getProperty("os.name").startsWith("Windows", true)) {
            Runtime.getRuntime().exec("shutdown -a")
        } else {
            Runtime.getRuntime().exec("shutdown -a")
        }
    }

}