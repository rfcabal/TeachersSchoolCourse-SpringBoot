package com.zreactor.profesoresplanta.service;

import com.zreactor.profesoresplanta.dao.SocialMediaDao;
import com.zreactor.profesoresplanta.model.SocialMedia;
import com.zreactor.profesoresplanta.model.TeacherSocialMedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("socialMediaService")
@Transactional
public class SocialMediaServiceImpl implements SocialMediaService {

    @Autowired
    private SocialMediaDao _socialMediaDao;

    @Override
    public void saveSocialMedia(SocialMedia socialMedia) {
        _socialMediaDao.saveSocialMedia(socialMedia);
    }

    @Override
    public List<SocialMedia> findAllSocialMedia() {
        return _socialMediaDao.findAllSocialMedia();
    }

    @Override
    public SocialMedia findById(Long idSocialMedia) {
        return _socialMediaDao.findById(idSocialMedia);
    }

    @Override
    public SocialMedia findByName(String name) {
        return _socialMediaDao.findByName(name);
    }

    @Override
    public TeacherSocialMedia findSocialMediaByIdAndName(Long idSocialMedia, String nickname) {
        return _socialMediaDao.findSocialMediaByIdAndName(idSocialMedia, nickname);
    }

    @Override
    public void deleteSocialMediaById(Long idSocialMedia) {
        _socialMediaDao.deleteSocialMediaById(idSocialMedia);
    }

    @Override
    public void updateSocialMedia(SocialMedia socialMedia) {
        _socialMediaDao.updateSocialMedia(socialMedia);
    }
}
