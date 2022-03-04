package com.bookingrest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="Reservation",
        uniqueConstraints={@UniqueConstraint(
                name = "reservationUniqueIndex", columnNames = {"checkin", "guest_id", "room_id"}) })
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ValidReservation
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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
    private Guest guest;
    @ManyToOne
    @JoinColumn(name="room_id", nullable=false)
    private Room room;
}