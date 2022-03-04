package com.bookingrest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name="Room", uniqueConstraints={ @UniqueConstraint(
        name = "roomUniqueIndex", columnNames = {"number", "floor"})} )
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int number;
    @Column
    private int floor;
    @Column
    private int capacity;
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private RoomType type;
    @Column(precision = 6, scale = 2)
    private BigDecimal price;

    @OneToMany(mappedBy = "room")
    Set<Reservation> reservations;
}
