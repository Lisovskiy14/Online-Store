package com.example.OnlineStore.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"subcategories", "products", "parent"})
@EqualsAndHashCode(exclude = {"subcategories", "products", "parent"})
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String slug;

    @ManyToOne
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> subcategories = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private List<Product> products = new ArrayList<>();
}
