package com.zreactor.profesoresplanta.service;

import com.zreactor.profesoresplanta.model.SocialMedia;
import com.zreactor.profesoresplanta.model.TeacherSocialMedia;

import java.util.List;

public interface SocialMediaService {

    void saveSocialMedia(SocialMedia socialMedia);

    List<SocialMedia> findAllSocialMedia();

    SocialMedia findById(int idSocialMedia);

    SocialMedia finByName(String name);

    TeacherSocialMedia findSocialMediaByIdAndName(int idSocialMedia, String nickname);

    void deleteSocialMediaById(int idSocialMedia);

    void updateSocialMedia(SocialMedia socialMedia);

}
