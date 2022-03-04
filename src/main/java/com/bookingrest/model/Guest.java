package com.bookingrest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="Guest")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Guest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToOne
    @JoinColumn(name="country_id", nullable=false)
    private Country country;
    @OneToMany(mappedBy = "guest")
    Set<Reservation> reservations;
}
