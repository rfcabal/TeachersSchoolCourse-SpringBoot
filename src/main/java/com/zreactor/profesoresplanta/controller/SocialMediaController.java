package com.zreactor.profesoresplanta.controller;


import com.zreactor.profesoresplanta.model.SocialMedia;
import com.zreactor.profesoresplanta.service.SocialMediaService;
import com.zreactor.profesoresplanta.util.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/v1")
public class SocialMediaController {

    @Autowired
    SocialMediaService _socialMediaService;

    // GET
    @RequestMapping(value = "/socialMedias", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<SocialMedia>> getSocialMedias(@RequestParam(value = "name", required = false) String nameSocialMedia,
                                                             @RequestParam(value = "id", required = false) Long idSocialMedia) {

        List<SocialMedia> socialMedias = new ArrayList<>();

        if(nameSocialMedia == null && idSocialMedia == null) {
            socialMedias = _socialMediaService.findAllSocialMedia();

        } else {

            if(nameSocialMedia != null) {
                SocialMedia socialMedia = _socialMediaService.findByName(nameSocialMedia);
                socialMedias.add(socialMedia);
            }

            // It is taken the getSocialMediaById instead of use this.
            if(idSocialMedia != null && idSocialMedia > 1) {
                getSocialMediaById(idSocialMedia);
            }

        }

        if(socialMedias.isEmpty()) {
            return new ResponseEntity(new CustomErrorType("idSocialMedia is required"), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<List<SocialMedia>>(socialMedias, HttpStatus.OK);
    }

    /*
    GET
    @param id
    */
    @RequestMapping(value = "/socialMedias/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<SocialMedia> getSocialMediaById(@PathVariable("id") Long idSocialMedia) {

        if(idSocialMedia == null || idSocialMedia <= 0) {
            return new ResponseEntity(new CustomErrorType("idSocialMedia is required"), HttpStatus.CONFLICT);
        }

        SocialMedia socialMedia = _socialMediaService.findById(idSocialMedia);

        if(socialMedia == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<SocialMedia>(socialMedia, HttpStatus.OK);
    }

    // POST
    @RequestMapping(value = "/socialMedias", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> createSocialMedia(@RequestBody SocialMedia socialMedia, UriComponentsBuilder uriComponentsBuilder) {

        if(socialMedia.getName().equals(null) || socialMedia.getName().isEmpty()) {
            return new ResponseEntity(new CustomErrorType("socialMedia name is required"), HttpStatus.CONFLICT);
        }

        if(_socialMediaService.findByName(socialMedia.getName()) != null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        _socialMediaService.saveSocialMedia(socialMedia);
        SocialMedia socialMedia2 = _socialMediaService.findByName(socialMedia.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/v1/socialMedias/{:id}")
                .buildAndExpand(socialMedia2.getIdSocialMedia())
                .toUri());

        return new ResponseEntity<String>(headers, HttpStatus.CREATED);

    }

    // UPDATE
    @RequestMapping(value = "/socialMedias/{id}", method = RequestMethod.PATCH, headers = "Accept=application/json")
    public ResponseEntity<?> updateSocialMedia(@PathVariable("id") Long idSocialMedia, @RequestBody SocialMedia socialMedia) {
        SocialMedia currentSocialMedia = _socialMediaService.findById(idSocialMedia);

        if (idSocialMedia == null || idSocialMedia <= 0) {
            return new ResponseEntity(new CustomErrorType("idSocialMedia is required"), HttpStatus.CONFLICT);
        }

        if (currentSocialMedia == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        currentSocialMedia.setName(socialMedia.getName());
        currentSocialMedia.setIcon(socialMedia.getIcon());

        _socialMediaService.updateSocialMedia(currentSocialMedia);

        return new ResponseEntity<SocialMedia>(currentSocialMedia, HttpStatus.OK);

    }

    // DELETE
    @RequestMapping(value = "/socialMedias/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<?> deleteSocialMedia(@PathVariable("id") Long idSocialMedia) {

        if (idSocialMedia == null || idSocialMedia <= 0) {
            return new ResponseEntity(new CustomErrorType("idSocialMedia is required"), HttpStatus.CONFLICT);
        }

        SocialMedia socialMedia = _socialMediaService.findById(idSocialMedia);
        if (socialMedia == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        _socialMediaService.deleteSocialMediaById(idSocialMedia);
        return new ResponseEntity<SocialMedia>(HttpStatus.OK);

    }

}
