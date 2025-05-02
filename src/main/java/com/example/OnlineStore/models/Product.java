package com.example.OnlineStore.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String imageUrl;

    private String category;

    @Column(length = 500)
    private String description;

    private int number;

    private double price;

    private String seller;
}
