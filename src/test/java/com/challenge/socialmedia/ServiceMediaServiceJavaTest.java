package com.challenge.socialmedia;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.challenge.socialmedia.dataobjects.SocialData;
import com.challenge.socialmedia.dataobjects.SocialDataResponse;
import com.challenge.socialmedia.dataobjects.SocialDataResult;
import com.challenge.socialmedia.dataobjects.SocialType;
import com.challenge.socialmedia.service.SocialMediaService;

import com.google.common.collect.Lists;

/**
 * Created on 10/22/17.
 *
 * @author Hugo Novajarque
 */
@RunWith(MockitoJUnitRunner.class)
public class ServiceMediaServiceJavaTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final List<SocialData> SOCIALS = Lists.newArrayList(
            new SocialData(SocialType.INSTAGRAM_PICTURE,
                           "1453799147568385409_1428837659",
                           LocalDateTime.parse("2017-02-01 00:00:00", FORMATTER),
                           "efteling",
                           "999999",
                           "This is a test",
                           BigDecimal.valueOf(54.9999),
                           BigDecimal.valueOf(109.9999)),
            new SocialData(SocialType.INSTAGRAM_VIDEO,
                           "1453799147568385409_1428837659",
                           LocalDateTime.parse("2017-04-01 00:00:00", FORMATTER),
                           "hugo",
                           "999999",
                           "This is a test challenge",
                           BigDecimal.valueOf(54.9999),
                           BigDecimal.valueOf(109.9999)),
            new SocialData(SocialType.FACEBOOK_POST,
                           "1453799147568385409_1428837659",
                           LocalDateTime.parse("2017-05-01 00:00:00", FORMATTER),
                           "hugo",
                           "999999",
                           "This is a test for greenhouse",
                           BigDecimal.valueOf(54.9999),
                           BigDecimal.valueOf(109.9999)),
            new SocialData(SocialType.INSTAGRAM_PICTURE,
                           "1453799147568385409_1428837659",
                           LocalDateTime.parse("2016-12-01 00:00:00", FORMATTER),
                           "efteling",
                           "999999",
                           "This is a real sentence",
                           BigDecimal
                                   .valueOf(54.9999),
                           BigDecimal.valueOf(109.9999))
    );

    private static final SocialDataResponse SOCIAL_RESPONSE_DATA = new SocialDataResponse(SOCIALS);

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SocialMediaService service;

    @Before
    public void setUp() {
        Mockito.when(restTemplate.getForObject(anyString(), eq(SocialDataResponse.class))).thenReturn(SOCIAL_RESPONSE_DATA);
    }

    @Test
    public void testForActorIdZero() throws ExecutionException, InterruptedException {

        CompletableFuture challenge = service.challenge(0);
        ResponseEntity responseEntity = (ResponseEntity) challenge.get();;
        SocialDataResult body = (SocialDataResult) responseEntity.getBody();

        assertEquals(body.getResults(), 2);

    }


}
