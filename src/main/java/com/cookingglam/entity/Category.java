package com.cookingglam.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private int id;
    private String name;
}
