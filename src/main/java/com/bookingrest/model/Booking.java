package com.bookingrest.model;

import com.bookingrest.util.BookingUtil;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

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
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    @Future
    private LocalDate checkin;

    @Column
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    @Future
    private LocalDate checkout;

    @Column(nullable = false)
    @Min(value = 1)
    private int people;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name="guest_id", nullable=false)
    @JsonIgnoreProperties(value = "bookings")
    private Guest guest;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
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
            return (int) ((BookingUtil.subtractDates(checkout, checkin)) / 1000 / 60 / 60 / 24);
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