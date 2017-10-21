package com.challenge.socialmedia.controller;

import static java.util.Objects.nonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.socialmedia.dataobjects.SocialType;
import com.challenge.socialmedia.service.SocialMediaService;

import kotlin.ranges.IntRange;

/**
 * Created on 10/21/17.
 *
 * @author Hugo Novajarque
 */
@RestController
public class SocialMediaController {

    private final SocialMediaService service;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final static String ERROR = "ERROR: No rule for this actor";

    @Autowired
    public SocialMediaController(SocialMediaService service) {
        this.service = service;
    }

    @GetMapping("challenge/{actorId}")
    public @ResponseBody CompletableFuture challenge(@PathVariable Integer actorId) {

        if (new IntRange(0, 6).contains(actorId)) {
            return service.challenge(actorId);
        } else {
            return CompletableFuture.supplyAsync(this::throwError);
        }
    }

    private Object throwError() {
        return ERROR;
    }

    @GetMapping("/data")
    public @ResponseBody
    CompletableFuture handleRequest(@RequestParam(required = false) String username,
                                    @RequestParam(required = false) String content,
                                    @RequestParam(required = false) String withoutContent,
                                    @RequestParam(required = false) SocialType socialType,
                                    @RequestParam(required = false) String before,
                                    @RequestParam(required = false) String after) throws ExecutionException, InterruptedException {

        LocalDateTime beforeDate = null;
        LocalDateTime afterDate = null;
        if (nonNull(before)) {
            beforeDate = LocalDateTime.parse(before, formatter);
        }

        if (nonNull(after)) {
            afterDate = LocalDateTime.parse(after, formatter);
        }

        return service.getSocials(username, content, withoutContent, socialType, beforeDate, afterDate);
    }
}
