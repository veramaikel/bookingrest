package com.bookingrest.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="Booking",
        uniqueConstraints={@UniqueConstraint(
                name = "bookingUniqueIndex", columnNames = {"checkin", "guest_id", "room_number"}) })
@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidBooking
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Booking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Future
    private Date checkin;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @Future
    private Date checkout;

    @Column(nullable = false)
    @Min(value = 1)
    private int people;

    @ManyToOne
    @JoinColumn(name="guest_id", nullable=false)
    @JsonIgnoreProperties(value = {"bookings", "id"})
    private Guest guest;

    @ManyToOne
    @JoinColumn(name="room_number", nullable=false)
    @JsonIgnoreProperties(value = "bookings")
    private Room room;

    @Transient
    public BigDecimal getTotal() {
        if(room == null)
            return BigDecimal.ZERO;

        return room.getPrice().multiply(new BigDecimal(getNights()));
    }

    @Transient
    public int getNights() {
        if (checkin == null || checkout == null) {
            return 0;
        } else {
            return (int) ((checkout.getTime() - checkin.getTime()) / 1000 / 60 / 60 / 24);
        }
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", checkin=" + checkin +
                ", checkout=" + checkout +
                ", people=" + people +
                ", guest=" + guest +
                ", room=" + room +
                ", total=" + getTotal() +
                ", nights=" + getNights() +
                '}';
    }
}