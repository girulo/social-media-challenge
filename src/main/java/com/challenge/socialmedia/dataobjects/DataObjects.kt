package com.challenge.socialmedia.dataobjects

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

/**
 * Created on 10/21/17.
 * @author Hugo Novajarque
 */

data class SocialDataResponse(
        val socials: List<SocialData>
)

data class SocialData(
        val socialType: SocialType,
        val socialId: String,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        val timestamp: LocalDateTime,
        val username: String,
        val userId: String,
        val content: String? = null,
        val latitude: BigDecimal? = null,
        val longitude: BigDecimal? = null
) {
    companion object {
    }
}

enum class SocialType {
    INSTAGRAM_PICTURE,
    INSTAGRAM_VIDEO,
    FACEBOOK_POST
}
