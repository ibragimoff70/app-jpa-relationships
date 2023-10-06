package com.example.appspringjparelationships.controller;

import com.example.appspringjparelationships.entity.Address;
import com.example.appspringjparelationships.entity.University;
import com.example.appspringjparelationships.payload.UniversityPayload;
import com.example.appspringjparelationships.repository.AddressRepository;
import com.example.appspringjparelationships.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/university")
public class UniversityController {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @GetMapping
    public List<University> getUniversities(){
        return universityRepository.findAll();
    }

    @GetMapping("/{id}")
    public University getUniversityById(@PathVariable Integer id){
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if (optionalUniversity.isEmpty()) {
            return null;
        }

        return optionalUniversity.get();
    }

    @PostMapping
    public String addUniversity(@RequestBody UniversityPayload universityPayload){
        Address address = new Address();
        address.setCity(universityPayload.getCity());
        address.setStreet(universityPayload.getStreet());
        Address savedAddress = addressRepository.save(address);

        University university = new University();
        university.setName(universityPayload.getName());
        university.setAddress(savedAddress);
        universityRepository.save(university);

        return "Saved successfully";
    }

    @PutMapping("/{id}")
    public String putUniversity(@PathVariable Integer id, @RequestBody UniversityPayload universityPayload){
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if (optionalUniversity.isEmpty()) {
            return "BUNDAY ID LIK UNIVER YOQ";
        }

        University university = optionalUniversity.get();
        university.setName(universityPayload.getName());
        Address currentAddress = university.getAddress();
        currentAddress.setCity(universityPayload.getCity());
        currentAddress.setStreet(universityPayload.getStreet());

        addressRepository.save(currentAddress);
        universityRepository.save(university);
        return "Saved successfully!";
    }

    @DeleteMapping("/{id}")
    public String deleteUniversity(@PathVariable Integer id){
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if (optionalUniversity.isEmpty()) {
            return "NOT FOUND ID";
        }

        universityRepository.deleteById(id);
        return "Deleted succesfully";
    }



}
