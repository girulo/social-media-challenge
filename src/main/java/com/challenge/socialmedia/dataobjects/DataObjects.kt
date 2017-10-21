package com.challenge.socialmedia.dataobjects

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * Created on 10/21/17.
 * @author Hugo Novajarque
 */

data class SocialDataResponse(
        val socials: List<SocialData>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
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