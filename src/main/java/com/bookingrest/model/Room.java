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
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "number")
public class Room implements Serializable {
    @Id
    private Integer number;

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
    private Set<Booking> bookings;
}
