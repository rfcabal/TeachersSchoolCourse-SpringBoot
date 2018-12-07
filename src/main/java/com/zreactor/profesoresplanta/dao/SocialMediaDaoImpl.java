package com.zreactor.profesoresplanta.dao;

import com.zreactor.profesoresplanta.model.SocialMedia;
import com.zreactor.profesoresplanta.model.TeacherSocialMedia;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class SocialMediaDaoImpl extends AbstractSession implements SocialMediaDao {

    @Override
    public void saveSocialMedia(SocialMedia socialMedia) {
        getSession().persist(socialMedia);
    }

    @Override
    public List<SocialMedia> findAllSocialMedia() {
        return getSession().createQuery("from SocialMedia").list();
    }

    @Override
    public SocialMedia findById(int idSocialMedia) {
        return (SocialMedia) getSession().get(SocialMedia.class, idSocialMedia);
    }

    @Override
    public SocialMedia finByName(String name) {
        return (SocialMedia) getSession()
                .createQuery("from SocialMedia where name = :name")
                .setParameter("name", name)
                .uniqueResult();
    }

    @Override
    public TeacherSocialMedia findSocialMediaByIdAndName(int idSocialMedia, String nickname) {
        List<Object[]> objects = getSession().createQuery("from TeacherSocialMedia tsm join tsm.socialMedia sm where sm.idSocialMedia = :idSocialMedia and tsm.nickname = :nickname")
                .setParameter("idSocialMedia", idSocialMedia)
                .setParameter("nickname", nickname).list();
        
        if(objects.size() > 0) {
            for (Object[] object2: objects) {
                for (Object object: object2) {
                    if(object instanceof TeacherSocialMedia) {
                        return (TeacherSocialMedia) object;
                    }
                }
            }
        }
        
        return null;
    }

    @Override
    public void deleteSocialMediaById(int idSocialMedia) {
        SocialMedia socialMedia = findById(idSocialMedia);
        if(socialMedia != null) {
            getSession().delete(socialMedia);
        }
    }

    @Override
    public void updateSocialMedia(SocialMedia socialMedia) {
        getSession().update(socialMedia);
    }
}
