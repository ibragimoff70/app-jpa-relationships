package com.example.appspringjparelationships.controller;

import com.example.appspringjparelationships.entity.Faculty;
import com.example.appspringjparelationships.entity.Group;
import com.example.appspringjparelationships.payload.GroupPayload;
import com.example.appspringjparelationships.repository.FacultyRepository;
import com.example.appspringjparelationships.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private FacultyRepository facultyRepository;


    @GetMapping
    public List<Group> getGroups(){
        return groupRepository.findAll();
    }

    @GetMapping("/byUniversityId/{universityId}")
    public List<Group> getGroupsById(@PathVariable Integer universityId){
//        List<Group> allByFacultyUniversityId = groupRepository.findAllByFaculty_University_Id(universityId);
        List<Group> groupByUniversityId = groupRepository.getGroupByUniversityId(universityId);
//        List<Group> groupByUniversityIdNative = groupRepository.getGroupByUniversityIdNative(universityId);
        return groupByUniversityId;
    }

    @PostMapping
    public String add(@RequestBody GroupPayload groupPayload){
        Group group = new Group();
        group.setName(groupPayload.getGroupName());

        Optional<Faculty> optionalFaculty = facultyRepository.findById(groupPayload.getFacultyId());
        if (optionalFaculty.isEmpty())
            return "NOT FOUND SUCH ID FACULTY";

        group.setFaculty(optionalFaculty.get());
        groupRepository.save(group);
        return "Group Added!";
    }

}
