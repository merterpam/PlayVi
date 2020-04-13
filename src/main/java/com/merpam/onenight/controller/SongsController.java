package com.merpam.onenight.controller;

import com.merpam.onenight.spotify.service.SpotifyWebService;
import com.merpam.onenight.spotify.service.model.SearchTracksResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.ws.rs.QueryParam;

@RestController
@RequestMapping("/songs")
public class SongsController {

    @Resource
    SpotifyWebService spotifyWebService;

    @GetMapping("/search")
    public SearchTracksResponse searchSongs(@QueryParam("query") String query,
                                           @QueryParam("limit") String limit,
                                           @QueryParam("offset") String offset) {
        return spotifyWebService.getSongs(query, limit, offset);
    }
}
