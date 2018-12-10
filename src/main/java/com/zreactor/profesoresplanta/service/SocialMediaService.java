package com.zreactor.profesoresplanta.service;

import com.zreactor.profesoresplanta.model.SocialMedia;
import com.zreactor.profesoresplanta.model.TeacherSocialMedia;

import java.util.List;

public interface SocialMediaService {

    void saveSocialMedia(SocialMedia socialMedia);

    List<SocialMedia> findAllSocialMedia();

    SocialMedia findById(Long idSocialMedia);

    SocialMedia findByName(String name);

    TeacherSocialMedia findSocialMediaByIdAndName(Long idSocialMedia, String nickname);

    void deleteSocialMediaById(Long idSocialMedia);

    void updateSocialMedia(SocialMedia socialMedia);

}
