package com.zreactor.profesoresplanta.dao;

import com.zreactor.profesoresplanta.model.Teacher;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class TeacherDaoImpl extends AbstractSession implements TeacherDao {

    @Override
    public void saveTeacher(Teacher teacher) {
        getSession().persist(teacher);
    }

    @Override
    public List<Teacher> findAllTeachers() {
        return getSession().createQuery("from Teacher").list();
    }

    @Override
    public Teacher findById(Long idTeacher) {
        return (Teacher) getSession().get(Teacher.class, idTeacher);
    }

    @Override
    public Teacher findByName(String name) {
        return (Teacher) getSession()
                .createQuery("from Teacher where name = :name")
                .setParameter("name", name)
                .uniqueResult();
    }

    @Override
    public void deleteTeacherById(Long idTeacher) {
        Teacher teacher = findById(idTeacher);
        if(teacher != null) {
            getSession().delete(teacher);
        }
    }

    @Override
    public void updateTeacher(Teacher teacher) {
        getSession().update(teacher);
    }
}
