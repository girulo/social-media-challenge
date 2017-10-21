package com.challenge.socialmedia

import com.challenge.socialmedia.dataobjects.SocialData
import com.challenge.socialmedia.dataobjects.SocialDataResponse
import com.challenge.socialmedia.dataobjects.SocialType
import com.challenge.socialmedia.service.SocialMediaService
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers
import org.mockito.MockitoAnnotations
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.test.assertEquals

/**
 * Created on 10/21/17.
 * @author Hugo Novajarque
 */

class SocialMediaServiceKotlinTest {

    private val FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    private val socials: List<SocialData> = listOf(
            SocialData(SocialType.INSTAGRAM_PICTURE, "1453799147568385409_1428837659", LocalDateTime.parse("2017-02-04 00:00:00", FORMATTER), "efteling", "999999", "This is a disney test", BigDecimal.valueOf(54.9999), BigDecimal.valueOf(109.9999)),
            SocialData(SocialType.INSTAGRAM_VIDEO, "1453799147568385409_1428837659", LocalDateTime.parse("2017-04-01 00:00:00", FORMATTER), "hugo", "999999", "This is a test challenge", BigDecimal.valueOf(54.9999), BigDecimal.valueOf(109.9999)),
            SocialData(SocialType.FACEBOOK_POST, "1453799147568385409_1428837659", LocalDateTime.parse("2017-05-01 00:00:00", FORMATTER), "hugo", "999999", "This is a test for greenhouse", BigDecimal.valueOf(54.9999), BigDecimal.valueOf(109.9999)),
            SocialData(SocialType.INSTAGRAM_PICTURE, "1453799147568385409_1428837659", LocalDateTime.parse("2016-12-01 00:00:00", FORMATTER), "efteling", "999999", "This is a real sentence", BigDecimal.valueOf(54.9999), BigDecimal.valueOf(109.9999))
            )
    private val socialResponseData = SocialDataResponse(socials)

    private val restTemplate: RestTemplate = mock<RestTemplate>()

    private lateinit var service: SocialMediaService

    @Before
    @Throws(Exception::class)
    fun setup() {
        MockitoAnnotations.initMocks(this)
        whenever(restTemplate.getForObject(Matchers.anyString(), Matchers.eq(SocialDataResponse::class.java))).thenReturn(socialResponseData)

        service = SocialMediaService(restTemplate)
    }

    @Test
    fun `Test for actor with actorId 0`() {

        val result = service.challenge(0).get()
        assertEquals(result.size, 2)
    }

    @Test
    fun `Test for actor with actorId 1`() {

        val result = service.challenge(1).get()
        assertEquals(result.size, 1)
    }

    @Test
    fun `Test for actor with actorId 2`() {

        val result = service.challenge(2).get()
        assertEquals(result.size, 3)
    }

    @Test
    fun `Test for actor with actorId 3`() {

        val result = service.challenge(3).get()
        assertEquals(result.size, 1)
    }

    @Test
    fun `Test for actor with actorId 4`() {

        val result = service.challenge(4).get()
        assertEquals(result.size, 1)
    }

    @Test
    fun `Test for actor with actorId 5`() {

        val result = service.challenge(5).get()
        assertEquals(result.size, 3)
    }

    @Test
    fun `Test for actor with actorId 6`() {

        val result = service.challenge(6).get()
        assertEquals(result.size, 1)
    }
}