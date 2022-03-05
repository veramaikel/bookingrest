package com.bookingrest.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private int number;

    @Column(nullable = false)
    private int floor;

    @Column(nullable = false)
    private int capacity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="type_id", nullable=false)
    private RoomType type;

    @Column(precision = 6, scale = 2, nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "room")
    @JsonIgnoreProperties(value = "room")
    private Set<Reservation> reservations;
}
