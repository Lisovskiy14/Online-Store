package com.example.OnlineStore.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(exclude = "category")
@EqualsAndHashCode(exclude = "category")
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(length = 1000)
    private String description;

    private int number;

    private double price;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;
}
