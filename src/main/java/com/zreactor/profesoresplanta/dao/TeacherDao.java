package com.zreactor.profesoresplanta.dao;

import com.zreactor.profesoresplanta.model.Teacher;

import java.util.List;

public interface TeacherDao {

    void saveTeacher(Teacher teacher);

    List<Teacher> findAllTeachers();

    Teacher findById(Long idTeacher);

    Teacher findByName(String name);

    void deleteTeacherById(Long idTeacher);

    void updateTeacher(Teacher teacher);

}
