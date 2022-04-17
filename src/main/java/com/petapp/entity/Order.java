package com.petapp.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "order_item_mapping",
            joinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "item_id")
    @Column(name = "items_ordered")
    private Map<Long, Integer> items;

    @Column(name = "sum_to_pay", precision = 10, scale = 2, nullable = false)
    @Type(type = "double")
    private double sumToPay;

    @Column(name = "creation_time", nullable = false)
    private Date creationTime;

    public Order() {
        this.creationTime = new Date();
    }

}
