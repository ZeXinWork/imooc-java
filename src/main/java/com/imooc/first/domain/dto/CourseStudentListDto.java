package com.imooc.first.domain.dto;

import com.imooc.first.domain.entity.Student;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


//获取每门课程报名的学生
@Data
public class CourseStudentListDto {
    private String courseName;
    private List<Student> studentList;  // 学生列表
    private Long courseId;
}
