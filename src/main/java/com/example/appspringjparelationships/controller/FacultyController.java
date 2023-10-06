package com.example.appspringjparelationships.controller;

import com.example.appspringjparelationships.entity.Faculty;
import com.example.appspringjparelationships.entity.University;
import com.example.appspringjparelationships.payload.FacultyPayload;
import com.example.appspringjparelationships.repository.FacultyRepository;
import com.example.appspringjparelationships.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @GetMapping
    public List<Faculty> getFacultyes(){
        return facultyRepository.findAll();
    }

    @PostMapping
    public String add(@RequestBody FacultyPayload facultyPayload){
        boolean existsByNameAndUniversityId = facultyRepository.existsByNameAndUniversityId(facultyPayload.getFacultyName(), facultyPayload.getUniversityId());
        if (existsByNameAndUniversityId) {
            return "Afsus qosha olmaysiz bu universitetda bunday Fakultet bor?";
        }

        Faculty faculty = new Faculty();
        faculty.setName(facultyPayload.getFacultyName());
        Optional<University> optionalUniversity = universityRepository.findById(facultyPayload.getUniversityId());
        if (optionalUniversity.isEmpty()) {
            return "Bunday ID lik univer topilmadi!";
        }
        faculty.setUniversity(optionalUniversity.get());
        facultyRepository.save(faculty);
        return "Saved succesfully";
    }

    @GetMapping("/byUniversityId/{universityId}")
    public List<Faculty> getFacultiesByUniversityId(@PathVariable Integer universityId){
        List<Faculty> allByUniversityId = facultyRepository.findAllByUniversityId(universityId);
        return allByUniversityId;
    }

    @DeleteMapping("/{id}")
    public String deleteFaculty(@PathVariable Integer id){
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isEmpty()) {
            return "No such elem!";
        }

        facultyRepository.deleteById(id);
        return "DELTED";
    }

    @PutMapping("/{id}")
    public String editFaculty(@PathVariable Integer id, @RequestBody FacultyPayload facultyPayload){
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isPresent()) {
            Faculty faculty = optionalFaculty.get();
            faculty.setName(facultyPayload.getFacultyName());
            Optional<University> optionalUniversity = universityRepository.findById(facultyPayload.getUniversityId());
            if (optionalUniversity.isPresent()) {
                faculty.setUniversity(optionalUniversity.get());
                facultyRepository.save(faculty);
                return "Faculty saved";
            }else {
                return "Bunday univer topilmadi";
            }
        }

        return "Faculty not found";
    }

}
