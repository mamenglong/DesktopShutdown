object BuildConfig {
    const val BUILD_TIME = "2023-09-21 15:40:56:583"
    private val osName = System.getProperty("os.name")
    val isMacOS = osName.startsWith("Mac", true)
    val isWindows = osName.startsWith("Windows", true)

    const val DEBUG = true
    const val VERSION_NAME = "1.0.5"
}