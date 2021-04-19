package com.example.search.model;

import com.example.search.enums.ESTATE;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Address {
    @Id
    private String id;

    private String line1;

    private String line2;

    private String city;

    @Enumerated(EnumType.STRING)
    private ESTATE ESTATE;

    private String pincode;
}