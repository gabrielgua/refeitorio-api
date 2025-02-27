package com.gabrielgua.refeitorio.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.*;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;

    private BigDecimal finalPrice;
    private BigDecimal originalPrice;
    private BigDecimal discountedPrice;

    @ManyToOne
    @JoinColumn(name = "credential")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "atendimento_id")
    private Atendimento atendimento;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    @CreationTimestamp
    private OffsetDateTime createdAt;

    @PrePersist
    private void generateOrderNumber() {
        var random = new Random();
        int code = 10000000 + random.nextInt(90000000); // 8 digits

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String currentDate = dateFormat.format(new Date());

        setNumber(String.valueOf(String.format("%s%d", currentDate, code)));
    }
}