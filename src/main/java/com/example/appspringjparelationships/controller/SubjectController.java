package com.example.appspringjparelationships.controller;

import com.example.appspringjparelationships.entity.Subject;
import com.example.appspringjparelationships.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;

    @PostMapping
    public String add(@RequestBody Subject subject){
        if (subjectRepository.existsByName(subject.getName())) {
            return "Subject already exist!";
        }

        subjectRepository.save(subject);
        return "Subject added";
    }

    @GetMapping
    public List<Subject> get(){
        return subjectRepository.findAll();
    }

    @GetMapping("/{id}")
    public Subject getById(@PathVariable Integer id){
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        return optionalSubject.orElse(null);
    }



}
