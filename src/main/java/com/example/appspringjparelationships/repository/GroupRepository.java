package com.example.appspringjparelationships.repository;

import com.example.appspringjparelationships.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {

    List<Group> findAllByFaculty_University_Id(Integer faculty_university_id);

    @Query("select gr from groups as gr where gr.faculty.university.id = :universityId")
    List<Group> getGroupByUniversityId(Integer universityId);


    @Query(value = "select *  from groups g inner join faculty f on f.id = g.faculty_id inner join university u on u.id = f.university_id where u.id = :uId;", nativeQuery = true)
    List<Group> getGroupByUniversityIdNative(@Param("uId") Integer uId);

}
