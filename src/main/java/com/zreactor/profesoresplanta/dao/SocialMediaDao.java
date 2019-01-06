package com.zreactor.profesoresplanta.dao;

import com.zreactor.profesoresplanta.model.SocialMedia;
import com.zreactor.profesoresplanta.model.TeacherSocialMedia;

import java.util.List;

public interface SocialMediaDao {

    void saveSocialMedia(SocialMedia socialMedia);

    List<SocialMedia> findAllSocialMedia();

    SocialMedia findById(Long idSocialMedia);

    SocialMedia findByName(String name);

    TeacherSocialMedia findSocialMediaByIdAndName(Long idSocialMedia, String nickname);

    TeacherSocialMedia findSocialMediaByIdTeacherAndIdSocialMedia(Long idTeacher, Long idSocialMedia);

    void deleteSocialMediaById(Long idSocialMedia);

    void updateSocialMedia(SocialMedia socialMedia);

}
