package com.bookingrest.repository;

import com.bookingrest.model.Booking;
import com.bookingrest.model.Guest;
import com.bookingrest.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookingRepository extends PagingAndSortingRepository<Booking, Integer> {

    Page<Booking> findAll(Pageable pageable);

    Page<Booking> findAllByCheckin(LocalDate date, Pageable pageable);

    Page<Booking> findAllByCheckout(LocalDate date, Pageable pageable);

    Page<Booking> findAllByPeople(int people, Pageable pageable);

    Page<Booking> findAllByGuest(Guest guest, Pageable pageable);

    Page<Booking> findAllByRoom(Room room, Pageable pageable);

    @Query("from Booking b where b.checkout is null ")
    Page<Booking> findAllOpen(Pageable pageable);

    @Query("from Booking b where (b.checkin >= ?1 and b.checkin <= ?2) or "
            +" (b.checkin < ?1 and (b.checkout = null or b.checkout <= ?2))")
    Page<Booking> findAllByRange(LocalDate min, LocalDate max, Pageable pageable);

    Booking findById(int id);

    Booking findByCheckinAndGuestAndRoom(LocalDate checkid, Guest guest, Room room);
}
