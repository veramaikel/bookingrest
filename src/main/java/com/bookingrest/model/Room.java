package com.bookingrest.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private BigDecimal price = new BigDecimal(0);

    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private Set<Booking> bookings = new HashSet<>();

    public void addBooking(Booking booking){
        bookings.add(booking);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return number.equals(room.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, floor, capacity, type);
    }

    @Override
    public String toString() {
        return "Room{" +
                "number=" + number +
                ", floor=" + floor +
                ", capacity=" + capacity +
                ", type=" + type +
                ", price=" + price +
                '}';
    }
}
