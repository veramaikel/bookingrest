package com.bookingrest.repository;

import com.bookingrest.model.Room;
import com.bookingrest.model.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RoomRepository extends PagingAndSortingRepository<Room, Integer> {

    Page<Room> findAll(Pageable pageable);

    Page<Room> findAllByFloor(int floor, Pageable pageable);

    Page<Room> findAllByCapacity(int capacity, Pageable pageable);

    Page<Room> findAllByType(RoomType type, Pageable pageable);

    //@Query("select r from Room r join r.reservations v where v.checkin > ?1 or (v.checkin < ?1 and v.checkout < ?1)")
    //Page<Room> getFreeRooms(Date date, Pageable pageable);

    @Query("select r from Room r left join r.reservations v "
            +" where (v.checkin >= ?1 and v.checkin <= ?2) or "
            +" (v.checkin < ?1 and (v.checkout = null or v.checkout <= ?2))")
    Page<Room> getFreeRoomsByRange(Date min, Date max, Pageable pageable);

    @Query("select r from Room r join r.reservations v"
            +" where (v.checkin >= ?1 and v.checkin <= ?2) or "
            +" (v.checkin < ?1 and (v.checkout = null or v.checkout <= ?2))")
    Page<Room> getReservedRoomsByRange(Date min, Date max, Pageable pageable);

    @Query("select r from Room r join r.reservations v")
    Page<Room> getAllReservedRooms(Pageable pageable);

    Room findByNumber(Integer number);
}
