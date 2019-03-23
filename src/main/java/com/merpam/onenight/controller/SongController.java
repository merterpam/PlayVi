package com.merpam.onenight.controller;

import com.merpam.onenight.spotify.SpotifyWebService;
import com.merpam.onenight.spotify.pojo.TracksResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/song")
public class SongController {

    @Resource
    SpotifyWebService spotifyWebService;

    @GetMapping("/search")
    public TracksResponse searchSong(@RequestParam("q") String query) {
        return spotifyWebService.getSongs(query);
    }
}
