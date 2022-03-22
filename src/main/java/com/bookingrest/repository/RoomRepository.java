package com.bookingrest.repository;

import com.bookingrest.model.Room;
import com.bookingrest.model.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Repository
public interface RoomRepository extends PagingAndSortingRepository<Room, Integer> {

    Page<Room> findAll(Pageable pageable);

    Page<Room> findAllByFloor(int floor, Pageable pageable);

    @Query("from Room r where r.capacity <= :capacity")
    Page<Room> findAllByCapacity(int capacity, Pageable pageable);

    @Query("from Room r where r.price <= :price")
    Page<Room> findAllByPrice(BigDecimal price, Pageable pageable);

    Page<Room> findAllByType(RoomType type, Pageable pageable);

    @Query("select r from Room r where r not in ( " +
            " select b.room from Booking b where ( b.checkin >= ?1 and b.checkin <= ?2 ) or "
            +" ( b.checkin < ?1 and ( b.checkout = null or b.checkout <= ?2 ) ) )")
    Page<Room> findAllFreeRoomsByRange(LocalDate min, LocalDate max, Pageable pageable);

    @Query("select r from Room r where r not in ( " +
            " select b.room from Booking b where ( b.checkin >= ?1 and b.checkin <= ?2 ) or " +
            " ( b.checkin < ?1 and ( b.checkout = null or b.checkout <= ?2 ) ) ) and " +
            " r.price = ( select MIN(price) from Room )")
    Page<Room> findAllCheapestFreeRoomsByRange(LocalDate min, LocalDate max, Pageable pageable);

    /*@Query("select r from Room r join r.bookings b"
            +" where ( b.checkin >= ?1 and b.checkin <= ?2 ) or "
            +" ( b.checkin < ?1 and ( b.checkout = null or b.checkout <= ?2 ) )")
    Page<Room> findAllBookedRoomsByRange(Date min, Date max, Pageable pageable);
    */
    @Query("select r from Room r join r.bookings b"
            +" where ( b.checkin >= ?1 and b.checkin <= ?2 ) or "
            +" ( b.checkin < ?1 and ( b.checkout = null or b.checkout <= ?2 ) )")
    Page<Room> findAllBookedRoomsByRange(LocalDate min, LocalDate max, Pageable pageable);

    @Query("select r from Room r join r.bookings b")
    Page<Room> findAllBookedRooms(Pageable pageable);

    Room findByNumber(int number);
}
