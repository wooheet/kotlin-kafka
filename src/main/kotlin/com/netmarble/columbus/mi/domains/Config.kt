package com.netmarble.columbus.mi.domains

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class Config(
    @JsonProperty("author") val author: String?,
    @JsonProperty("text") val text: String?,
    @JsonProperty("timestamp") var timestamp: String?
) {
    fun createTimestamp() {
        LocalDateTime.now().toString().also { this.timestamp = it }
    }
}