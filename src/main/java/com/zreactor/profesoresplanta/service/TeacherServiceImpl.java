package com.zreactor.profesoresplanta.service;

import com.zreactor.profesoresplanta.dao.TeacherDao;
import com.zreactor.profesoresplanta.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("teacherService")
@Transactional
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherDao _teacherDao;

    @Override
    public void saveTeacher(Teacher teacher) {
        _teacherDao.saveTeacher(teacher);
    }

    @Override
    public List<Teacher> findAllTeachers() {
        return _teacherDao.findAllTeachers();
    }

    @Override
    public Teacher findById(int idTeacher) {
        return _teacherDao.findById(idTeacher);
    }

    @Override
    public Teacher findByName(String name) {
        return _teacherDao.findByName(name);
    }

    @Override
    public void deleteTeacherById(int idTeacher) {
        _teacherDao.deleteTeacherById(idTeacher);
    }

    @Override
    public void updateTeacher(Teacher teacher) {
        _teacherDao.updateTeacher(teacher);
    }
}
