package com.merpam.onenight.controller;

import com.merpam.onenight.spotify.SpotifyWebService;
import com.merpam.onenight.spotify.pojo.TracksResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/song")
@CrossOrigin
public class SongController {

    @Resource
    SpotifyWebService spotifyWebService;

    @GetMapping("/search")
    public TracksResponse searchSong(@RequestParam("q") String query,
                                     @RequestParam("limit") String limit,
                                     @RequestParam("offset") String offset) {
        return spotifyWebService.getSongs(query, limit, offset);
    }
}