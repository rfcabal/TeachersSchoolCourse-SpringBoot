package com.zreactor.profesoresplanta.dao;

import com.zreactor.profesoresplanta.model.Course;

import java.util.List;

public interface CourseDao {

    void saveCourse(Course course);

    List<Course> findAllCourses();

    Course findById(Long idCourse);

    Course findByName(String name);

    List<Course> findByIdTeacher(Long idTeacher);

    void deleteCourseById(Long idCourse);

    void updateCourse(Course course);

}
