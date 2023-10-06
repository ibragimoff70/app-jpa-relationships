package com.example.appspringjparelationships.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne(optional = false)
    private Address address;
}
