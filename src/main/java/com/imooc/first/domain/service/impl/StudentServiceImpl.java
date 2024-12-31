package com.imooc.first.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.first.domain.entity.Student;
import com.imooc.first.domain.entity.StudentCourse;
import com.imooc.first.domain.mapper.StudentCourseMapper;
import com.imooc.first.domain.mapper.StudentMapper;
import com.imooc.first.domain.service.StudentCourseService;
import com.imooc.first.domain.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    StudentCourseService studentCourseService;


    // 分页查询学生信息
    public IPage<Student> getStudentList(int pageNum, int pageSize, String name, String phoneNumber) {
        Page<Student> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            queryWrapper.like("name", name);
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            queryWrapper.eq("phoneNumber", phoneNumber);
        }
        return this.page(page, queryWrapper);
    }


//    //查询该学生的课程信息
//    @Override
//    public List<StudentCourse> getStudentList(Integer id) {
//
//    }

    //新增学员
    @Override
    public Boolean addStudent(Student student) {
        // 校验传入的学生对象是否为 null
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        // 校验学生姓名和手机号是否为空
        String studentName = student.getName();
        String studentPhoneNumber = student.getPhoneNumber();
        if (studentName == null || studentName.trim().isEmpty() || studentPhoneNumber == null || studentPhoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name and phone number cannot be null or empty");
        }


        // 查询已有学员，如果存在则更新，否则插入
        Student existingStudent = this.getSame(studentName, studentPhoneNumber);
        if (existingStudent != null) {
            // 如果学员已存在，更新该学员的 id
            student.setId(existingStudent.getId());
        }

        // 执行 saveOrUpdate 操作
        return this.saveOrUpdate(student);
    }


    public Student getSame(String studentName, String studentPhoneNumber) {
        // 查询是否已有相同的学员
        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentLambdaQueryWrapper.eq(Student::getName, studentName).eq(Student::getPhoneNumber, studentPhoneNumber);

        // 查询已有学员，如果存在则更新，否则插入
        return this.getOne(studentLambdaQueryWrapper);
    }


}
