package com.zreactor.profesoresplanta.dao;

import com.zreactor.profesoresplanta.model.Teacher;

import java.util.List;

public interface TeacherDao {

    void saveTeacher(Teacher teacher);

    List<Teacher> findAllTeachers();

    Teacher findById(int idTeacher);

    Teacher findByName(String name);

    void deleteTeacherById(int idTeacher);

    void updateTeacher(Teacher teacher);

}
