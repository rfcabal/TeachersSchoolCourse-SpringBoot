package com.zreactor.profesoresplanta.controller;


import com.zreactor.profesoresplanta.model.SocialMedia;
import com.zreactor.profesoresplanta.service.SocialMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/v1")
public class SocialMediaController {

    @Autowired
    SocialMediaService _socialMediaService;

    // GET
    @RequestMapping(value = "/socialMedias", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<SocialMedia>> getSocialMedias() {
        List<SocialMedia> socialMedias = new ArrayList<>();
        socialMedias = _socialMediaService.findAllSocialMedia();

        if(socialMedias.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<SocialMedia>>(socialMedias, HttpStatus.OK);
    }

}
