package com.challenge.socialmedia.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.challenge.socialmedia.dataobjects.SocialData;
import com.challenge.socialmedia.dataobjects.SocialDataResponse;

/**
 * Created on 10/21/17.
 *
 * @author Hugo Novajarque
 */
@Service
public class SocialMediaService {

    private static final String SOCIAL_URL = "https://s3-eu-west-1.amazonaws.com/snipperwall-challenge/socials.json";

    public List<SocialData> getSocials() {

        RestTemplate restTemplate = new RestTemplate();
        List<SocialData> resultList = restTemplate.getForObject(SOCIAL_URL, SocialDataResponse.class).getSocials();

        resultList = resultList.stream()
                               .filter(socialData -> Objects.nonNull(socialData.getContent()))
                               .filter(socialData -> socialData.getContent().contains("disney"))
                               .collect(Collectors.toList());
        return resultList;

        //        http://javarevisited.blogspot.com/2017/02/how-to-consume-json-from-restful-web-services-Spring-RESTTemplate-Example.html#ixzz4w9G5Oo47
    }
}
