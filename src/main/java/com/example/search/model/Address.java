package com.example.search.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "unit")
    private String unit;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false, insertable = false, updatable = false)
    private Person person;

    @Setter
    @Column(name = "person_id", nullable = false)
    private Long personId;

    @OneToMany(mappedBy = "address")
    private List<AddressTel> addressTelList;


}