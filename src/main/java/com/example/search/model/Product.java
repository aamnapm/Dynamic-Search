package com.example.search.model;


import com.example.search.enums.ECategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Product {
    @Id
    private String id;

    private String name;

    private Double price;

    private LocalDateTime manufacturingDate;

    @ManyToOne
    private Address manufacturingPlace;

    private Double weight;

    @Embedded
    private Dimension dimension;

    @ManyToOne
    private Distributor distributor;

    @Enumerated(EnumType.STRING)
    private ECategory ECategory;

    @Embeddable
    @Getter
    @Setter
    public static class Dimension {
        private Double height;

        private Double width;
    }


}