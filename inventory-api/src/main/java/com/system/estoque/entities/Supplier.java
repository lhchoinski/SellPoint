package com.system.estoque.entities;

import com.system.estoque.entities.base.BaseSoftDeleteEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "suppliers")
public class Supplier extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String cnpj;

    private String address;

    private String phone;

    private String email;

    private Boolean active;

}
