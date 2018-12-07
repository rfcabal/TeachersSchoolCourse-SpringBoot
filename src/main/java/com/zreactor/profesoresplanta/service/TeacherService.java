package com.zreactor.profesoresplanta.service;

import com.zreactor.profesoresplanta.model.Teacher;

import java.util.List;

public interface TeacherService {

    void saveTeacher(Teacher teacher);

    List<Teacher> findAllTeachers();

    Teacher findById(int idTeacher);

    Teacher findByName(String name);

    void deleteTeacherById(int idTeacher);

    void updateTeacher(Teacher teacher);

}
