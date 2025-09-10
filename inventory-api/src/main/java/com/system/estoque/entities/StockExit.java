package com.system.estoque.entities;

import com.system.estoque.entities.base.BaseSoftDeleteEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "stock_exits")
public class StockExit extends BaseSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;

    private Integer quantity;

    private LocalDateTime date_exit;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @Column(name = "user_id")
    private UUID userId;

}
