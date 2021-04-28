package com.example.search.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "address_tel")
public class AddressTel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tel")
    private String tel;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false, insertable = false, updatable = false)
    private Address address;

    @Setter
    @Column(name = "address_id", nullable = false)
    private Long addressId;
}
