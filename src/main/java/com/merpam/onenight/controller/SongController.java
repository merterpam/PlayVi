package com.merpam.onenight.controller;

import com.merpam.onenight.spotify.service.SpotifyWebService;
import com.merpam.onenight.spotify.service.model.SearchTracksResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/song")
@CrossOrigin(origins = {"https://dbilgili.github.io"}, allowCredentials = "true")
public class SongController {

    @Resource
    SpotifyWebService spotifyWebService;

    @GetMapping("/search")
    public SearchTracksResponse searchSong(@RequestParam("q") String query,
                                           @RequestParam("limit") String limit,
                                           @RequestParam("offset") String offset) {
        return spotifyWebService.getSongs(query, limit, offset);
    }
}
