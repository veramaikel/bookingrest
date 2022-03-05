package com.bookingrest.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Reservation implements Serializable {
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
    @JsonIgnoreProperties(value = {"reservations", "id"})
    private Guest guest;

    @ManyToOne
    @JoinColumn(name="room_id", nullable=false)
    @JsonIgnoreProperties(value = {"reservations", "id"})
    private Room room;
}