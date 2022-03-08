package com.bookingrest.service;

import com.bookingrest.model.Booking;
import com.bookingrest.model.Guest;
import com.bookingrest.model.Room;
import com.bookingrest.repository.BookingRepository;
import com.bookingrest.util.BookingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BookingService {

    BookingRepository repository;
    RoomService roomServ;
    GuestService guestServ;
    Sort defaultSort;

    @Autowired
    public BookingService(BookingRepository repository, RoomService roomServ, GuestService guestServ) {
        this.repository = repository;
        this.roomServ = roomServ;
        this.guestServ = guestServ;
        defaultSort = Sort.by("checkin").and(Sort.by("checkout"));
    }

    public List<Booking> findAllBookings(Pageable pageable){
        return repository.findAll(BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Booking> findAllBookingsByCheckin(Date date, Pageable pageable){
        return repository.findAllByCheckin(date, BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Booking> findAllBookingsByCheckout(Date date, Pageable pageable){
        return repository.findAllByCheckout(date, BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Booking> findAllBookingsByPeople(int people, Pageable pageable){
        return repository.findAllByPeople(people, BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Booking> findAllBookingsByRoom(int number, Pageable pageable){
        return repository.findAllByRoom(roomServ.findRoomByNumber(number),
                BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Booking> findAllBookingsByGuest(int id, Pageable pageable){
        return repository.findAllByGuest(guestServ.findGuestById(id),
                BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Booking> findAllBookingsByDate(Date date, Pageable pageable){
        return repository.findAllByRange(date, date,
                BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Booking> findAllBookingsByRange(Date date1, Date date2, Pageable pageable){
        return repository.findAllByRange(BookingUtil.newer(date1, date2), BookingUtil.newer(date1, date2),
                BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public Booking findBookingById(int id){
        return repository.findById(id);
    }

    @Transactional
    public Booking saveBooking(Booking booking){
        return repository.save(setPersistent(booking));
    }

    @Transactional
    public Booking updateBooking(Booking booking){
        return repository.save(setPersistent(booking));
    }

    private Booking setPersistent(Booking booking){
        Room room = booking.getRoom();
        if(room.getNumber()!=null) {
            room = roomServ.findRoomByNumber(room.getNumber());
            booking.setRoom(room);
        }
        Guest guest = booking.getGuest();
        if(guest.getId()!=null) {
            guest = guestServ.findGuestById(guest.getId());
            booking.setGuest(guest);
        }

        return booking;
    }

    @Transactional
    public boolean deleteBooking(int id) {
        try{
            repository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
