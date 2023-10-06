package com.example.appspringjparelationships.controller;

import com.example.appspringjparelationships.entity.Student;
import com.example.appspringjparelationships.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {


    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/forMinistry")
    public Page<Student> getAllForMinistry(@RequestParam int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> all = studentRepository.findAll(pageable);
        return all;
    }
    @GetMapping("/forUniversity/{id}")
    public Page<Student> getAllForUniversity(@PathVariable Integer id, @RequestParam int page){

        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> allByGroupFacultyUniversityId = studentRepository.findAllByGroup_Faculty_UniversityId(id, pageable);
        return allByGroupFacultyUniversityId;
    }



}
