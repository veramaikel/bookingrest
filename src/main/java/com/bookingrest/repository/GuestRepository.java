package com.bookingrest.repository;

import com.bookingrest.model.Country;
import com.bookingrest.model.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface GuestRepository extends PagingAndSortingRepository<Guest, Integer> {

    Page<Guest> findAll(Pageable pageable);

    Page<Guest> findAllByNameContains(String name, Pageable pageable);

    Page<Guest> findAllByCountry(Country country, Pageable pageable);

    @Query("select g from Guest g join g.bookings b"
            +" where (b.checkin >= ?1 and b.checkin <= ?2) or "
            +" (b.checkin < ?1 and (b.checkout = null or b.checkout <= ?2))")
    Page<Guest> getHaveBookedByRange(Date min, Date max, Pageable pageable);

    @Query("select g from Guest g join g.bookings b")
    Page<Guest> getAllHaveBooked(Pageable pageable);

    Guest findById(int id);

    Guest findByName(String name);
}
