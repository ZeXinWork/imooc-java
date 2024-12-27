package com.imooc.first.domain.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@ConfigurationProperties(prefix = "student")
public class UserController {

    private String name;
    private int age;
    private String grade;


    @GetMapping("/info/{name}")
    public String getUserName(@PathVariable("name") String name) {
        return "Hello " + name;
    }

    @GetMapping("/port")
    public String getPort(@Value("${server.port}") String port) {
        return "port: " + port;
    }

    @GetMapping("/student")
    public String getStudentInfo() {
        return "name: " + name + ", age: " + age + ", grade: " + grade;
    }

    /**
     * 获取
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     *
     * @return age
     */
    public int getAge() {
        return age;
    }

    /**
     * 设置
     *
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * 获取
     *
     * @return grade
     */
    public String getGrade() {
        return grade;
    }

    /**
     * 设置
     *
     * @param grade
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String toString() {
        return "UserController{name = " + name + ", age = " + age + ", grade = " + grade + "}";
    }
}
