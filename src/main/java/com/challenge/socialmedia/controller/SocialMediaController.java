package com.challenge.socialmedia.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.socialmedia.dataobjects.SocialData;
import com.challenge.socialmedia.dataobjects.SocialType;
import com.challenge.socialmedia.service.SocialMediaService;

/**
 * Created on 10/21/17.
 *
 * @author Hugo Novajarque
 */
@RestController
public class SocialMediaController {

    private final SocialMediaService service;

    @Autowired
    public SocialMediaController(SocialMediaService service) {
        this.service = service;
    }

    @GetMapping("/data")
    public @ResponseBody
    List<SocialData> handleRequest(@RequestParam(required = false) String username,
                                   @RequestParam(required = false) String content,
                                   @RequestParam(required = false) String withoutContent,
                                   @RequestParam(required = false) SocialType socialType,
                                   @RequestParam(required = false) LocalDate before,
                                   @RequestParam(required = false) LocalDate after) {

        return service.getSocials();

    }
}
