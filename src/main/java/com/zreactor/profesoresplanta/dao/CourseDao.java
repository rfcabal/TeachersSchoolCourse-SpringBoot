package com.zreactor.profesoresplanta.dao;

import com.zreactor.profesoresplanta.model.Course;

import java.util.List;

public interface CourseDao {

    void saveCourse(Course course);

    List<Course> findAllCourses();

    Course findById(int idCourse);

    Course findByName(String name);

    List<Course> findByTeacher(int idTeacher);

    void deleteCourseById(int idCourse);

    void updateCourse(Course course);

}
