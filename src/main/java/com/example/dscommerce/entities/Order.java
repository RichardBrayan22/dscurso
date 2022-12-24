package com.example.dscommerce.entities;

import java.time.Instant;

import jakarta.persistence.*;


@Entity
@Table(name="tb_order")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant moment;
    private OrderStatus status;
    
    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

}
