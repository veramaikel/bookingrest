package com.bookingrest.model;

import com.fasterxml.jackson.annotation.*;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Guest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name="country_id", nullable=false)
    @JsonIgnoreProperties(value = {"guests", "id"})
    private Country country;

    @OneToMany(mappedBy = "guest")
    @JsonIgnoreProperties(value = "guest")
    private Set<Reservation> reservations;
}
