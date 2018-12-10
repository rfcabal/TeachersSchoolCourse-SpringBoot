package com.zreactor.profesoresplanta.service;

import com.zreactor.profesoresplanta.dao.CourseDao;
import com.zreactor.profesoresplanta.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("courseService")
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao _courseDao;

    @Override
    public void saveCourse(Course course) {
        _courseDao.saveCourse(course);
    }

    @Override
    public List<Course> findAllCourses() {
        return _courseDao.findAllCourses();
    }

    @Override
    public Course findById(Long idCourse) {
        return _courseDao.findById(idCourse);
    }

    @Override
    public Course findByName(String name) {
        return _courseDao.findByName(name);
    }

    @Override
    public List<Course> findByIdTeacher(Long idTeacher) {
        return _courseDao.findByIdTeacher(idTeacher);
    }

    @Override
    public void deleteCourseById(Long idCourse) {
        _courseDao.deleteCourseById(idCourse);
    }

    @Override
    public void updateCourse(Course course) {
        _courseDao.updateCourse(course);
    }
}
