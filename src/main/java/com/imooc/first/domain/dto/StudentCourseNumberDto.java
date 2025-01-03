package com.imooc.first.domain.dto;

import lombok.Data;


//查询每个学生报了多少门课
@Data
public class StudentCourseNumberDto {
    private String name; //学生姓名
    private Long id; //学生id
    private Integer courseNumber;//报名的课程数量
}
