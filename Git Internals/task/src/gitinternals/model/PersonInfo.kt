package gitinternals.model

import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

data class PersonInfo(val name: String, val email: String, val timestamp: Long, val timezone: String) {
    companion object {
        fun parse(info: String): PersonInfo {
            val emailStartIndex = info.indexOf('<')
            val emailEndIndex = info.indexOf('>')

            val name = info.substring(0, emailStartIndex).trim()
            val email = info.substring(emailStartIndex + 1, emailEndIndex)

            val timestampParts = info.substring(emailEndIndex + 1).trim().split(" ")
            val timestamp = timestampParts[0].toLong()
            val timezone = timestampParts[1]

            return PersonInfo(name, email, timestamp, timezone)
        }
    }

    fun formatTimestamp(): String {
        return Instant.ofEpochSecond(timestamp)
            .atZone(ZoneOffset.of(timezone))
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss xxx"))
    }
}
