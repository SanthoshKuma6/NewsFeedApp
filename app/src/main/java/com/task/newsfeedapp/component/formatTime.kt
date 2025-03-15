package com.task.newsfeedapp.component
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.Locale
import java.time.Duration
import java.time.OffsetDateTime
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun formatTime(time: String?): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yy", Locale.getDefault())
        val date = inputFormat.parse(time ?: "")
        date?.let { "publish on ${outputFormat.format(it)}" } ?: "N/A"
    } catch (e: Exception) {
        "Invalid Time"
    }
}



@RequiresApi(Build.VERSION_CODES.O)
fun calculateReadTimeWithDateCheck(timestamp: String): String {
    val fixedTimestamp = timestamp.replace("+0000", "+00:00")

    val start = OffsetDateTime.parse(fixedTimestamp, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    val now = OffsetDateTime.now(ZoneOffset.UTC)

    val duration = Duration.between(start, now)

    val seconds = duration.seconds
    val minutes = duration.toMinutes()
    val hours = duration.toHours()

    val isBeforeCutoff = start.isBefore(OffsetDateTime.of(2024, 11, 19, 0, 0, 0, 0, ZoneOffset.UTC))

    return when {
        hours > 0 -> {
            val remainingMinutes = (minutes % 60)
            val remainingSeconds = (seconds % 60)
            "$hours hour${if (hours > 1) "s" else ""}, $remainingMinutes min, $remainingSeconds sec read" +
                    if (isBeforeCutoff) " (Before 19th Nov 2024)" else ""
        }
        minutes > 0 -> {
            val remainingSeconds = (seconds % 60)
            "$minutes min, $remainingSeconds sec read" +
                    if (isBeforeCutoff) " (Before 19th Nov 2024)" else ""
        }
        else -> {
            "$seconds sec read" +
                    if (isBeforeCutoff) " (Before 19th Nov 2024)" else ""
        }
    }
}




