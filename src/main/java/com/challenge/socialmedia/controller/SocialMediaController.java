package com.challenge.socialmedia.controller;

import static java.util.Objects.nonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final static String ERROR = "ERROR: No rule for this actor";

    private final SocialMediaService service;

    @Autowired
    public SocialMediaController(SocialMediaService service) {
        this.service = service;
    }

    @GetMapping("challenge/{actorId}")
    public @ResponseBody CompletableFuture challenge(@PathVariable Integer actorId) {

        return new IntRange(0, 6).contains(actorId) ?
                service.challenge(actorId) : CompletableFuture.supplyAsync(this::throwError);

    }

    @GetMapping("/challenge")
    public @ResponseBody
    CompletableFuture handleRequest(@RequestParam(required = false) String username,
                                                                      @RequestParam(required = false) String content,
                                                                      @RequestParam(required = false) String withoutContent,
                                                                      @RequestParam(required = false) SocialType socialType,
                                                                      @RequestParam(required = false) String before,
                                                                      @RequestParam(required = false) String after) throws ExecutionException, InterruptedException {

        LocalDateTime beforeDate = toLocalDateTime(before);
        LocalDateTime afterDate = toLocalDateTime(after);

        return service.getSocials(username, content, withoutContent, socialType, beforeDate, afterDate);
    }

    private ResponseEntity<String> throwError() {
        return new ResponseEntity<>(ERROR, HttpStatus.BAD_REQUEST);
    }

    private LocalDateTime toLocalDateTime(String date) {

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return nonNull(date) ? LocalDateTime.parse(date, formatter) : null;
    }
}
