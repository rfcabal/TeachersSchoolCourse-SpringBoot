package com.zreactor.profesoresplanta.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "social_media")
public class SocialMedia implements Serializable {

    @Id
    @Column(name = "id_social_media")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSocialMedia;
    @Column(name = "name")
    private String name;
    @Column(name = "icon")
    private String icon;
    @OneToMany
    @JoinColumn(name = "id_social_media")
    private Set<SocialMedia> teacherSocialMedia;

    public SocialMedia(String name, String icon) {
        super();
        this.name = name;
        this.icon = icon;
    }

    public SocialMedia() {
        super();
    }

    public SocialMedia(int idSocialMedia) {
        super();
    }


    public int getIdSocialMedia() {
        return idSocialMedia;
    }

    public void setIdSocialMedia(int idSocialMedia) {
        this.idSocialMedia = idSocialMedia;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Set<SocialMedia> getTeacherSocialMedia() {
        return teacherSocialMedia;
    }

    public void setTeacherSocialMedia(Set<SocialMedia> teacherSocialMedia) {
        this.teacherSocialMedia = teacherSocialMedia;
    }
}
