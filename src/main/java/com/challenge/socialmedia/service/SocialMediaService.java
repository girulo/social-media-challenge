package com.challenge.socialmedia.service;

import static java.util.Objects.nonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.challenge.socialmedia.dataobjects.SocialData;
import com.challenge.socialmedia.dataobjects.SocialDataResponse;
import com.challenge.socialmedia.dataobjects.SocialType;

/**
 * Created on 10/21/17.
 *
 * @author Hugo Novajarque
 */
@Service
public class SocialMediaService {

    private static final String SOCIAL_URL = "https://s3-eu-west-1.amazonaws.com/snipperwall-challenge/socials.json";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final String USERNAME = "efteling";
    private static final String CONTENT = "disney";
    private static final SocialType VIDEO_CONTENT = SocialType.INSTAGRAM_VIDEO;
    private static final LocalDateTime DATE = LocalDateTime.parse("2017-02-01 00:00:00", FORMATTER);
    private static final SocialType FACEBOOK_CONTENT = SocialType.FACEBOOK_POST;

    private final RestTemplate restTemplate;

    @Autowired
    public SocialMediaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Returns a CompletableFuture that will contain the result for the specific actor
     * @param actorId
     * @return
     */
    public CompletableFuture<List<SocialData>> challenge(Integer actorId) {

        return CompletableFuture.supplyAsync(this::makeRESTCall)
                                .thenApply(socialDataList -> filterResultsByActorId(socialDataList, actorId));
    }

    /**
     * Returns a CompletableFuture that will contain the result regarding which filter we will apply
     * @param username
     * @param content
     * @param withoutContent
     * @param socialType
     * @param before
     * @param after
     * @return
     */
    public CompletableFuture getSocials(String username,
                                        String content,
                                        String withoutContent,
                                        SocialType socialType,
                                        LocalDateTime before, LocalDateTime after) {


        return CompletableFuture.supplyAsync(this::makeRESTCall)
                                .thenApply(socialDataList -> filterResults(username,
                                                                           content,
                                                                           withoutContent,
                                                                           socialType,
                                                                           before,
                                                                           after,
                                                                           socialDataList));
    }

    /**
     * Makes a REST call to the SOCIAL_URL to fetch all the data
     * @return
     */
    @NotNull
    private List<SocialData> makeRESTCall() {
//        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(SOCIAL_URL, SocialDataResponse.class).getSocials();
    }


    /*********************
     * Auxiliary methods
     *********************/

    private List<SocialData> filterResultsByActorId(List<SocialData> socialDataList,
                                                    Integer actorId) {

        Predicate<SocialData> filter = getFilterForActor(actorId);

        return filter != null ? socialDataList.stream()
                                               .filter(filter)
                                               .collect(Collectors.toList()) : socialDataList;
    }

    @Nullable
    private Predicate<SocialData> getFilterForActor(Integer actorId) {
        Predicate<SocialData> filter = null;
        switch (actorId) {
            case 0:
                filter = socialData -> socialData.getUsername().equals(USERNAME);
                break;
            case 1:
                filter = socialData -> nonNull(socialData.getContent()) && socialData.getContent().contains(CONTENT);
                break;
            case 2:
                filter = socialData -> nonNull(socialData.getContent()) && !socialData.getContent().contains(CONTENT);
                break;
            case 3:
                filter = socialData -> socialData.getSocialType().equals(VIDEO_CONTENT);
                break;
            case 4:
                filter = socialData -> socialData.getTimestamp().isBefore(DATE);
                break;
            case 5:
                filter = socialData -> socialData.getTimestamp().isAfter(DATE);
                break;
            case 6:
                filter = socialData -> socialData.getSocialType().equals(FACEBOOK_CONTENT);
                break;
        }
        return filter;
    }

    private List<SocialData> filterResults(String username,
                                           String content,
                                           String withoutContent,
                                           SocialType socialType,
                                           LocalDateTime before, LocalDateTime after, List<SocialData> resultList) {

        Predicate<SocialData> applyFilter =
                getFilterForRequest(username, content, withoutContent, socialType, before, after);

        return applyFilter != null ? resultList.stream()
                                               .filter(applyFilter)
                                               .collect(Collectors.toList()) : resultList;
    }

    @Nullable
    private Predicate<SocialData> getFilterForRequest(String username,
                                                      String content,
                                                      String withoutContent,
                                                      SocialType socialType,
                                                      LocalDateTime before,
                                                      LocalDateTime after) {
        Predicate<SocialData> applyFilter = null;

        if (nonNull(username) && !username.isEmpty()) {
            applyFilter = socialData -> socialData.getUsername().equals(username);
        } else if (nonNull(content)) {
            applyFilter = socialData -> nonNull(socialData.getContent()) && socialData.getContent().contains(content);
        } else if (nonNull(withoutContent)) {
            applyFilter =
                    socialData -> nonNull(socialData.getContent()) && !socialData.getContent().contains(withoutContent);
        } else if (nonNull(socialType)) {
            applyFilter = socialData -> socialData.getSocialType().equals(socialType);
        } else if (nonNull(before)) {
            applyFilter = socialData -> socialData.getTimestamp().isBefore(before);
        } else if (nonNull(after)) {
            applyFilter = socialData -> socialData.getTimestamp().isAfter(after);
        }
        return applyFilter;
    }
}
