package com.bookingrest.controller;

import com.bookingrest.model.Booking;
import com.bookingrest.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/booking")
public class BookingController {

    BookingService service;

    @Autowired
    public BookingController(BookingService service){
        this.service = service;
    }

    @GetMapping("all")
    public List<Booking> getBookings(Pageable pageable){
        return service.findAllBookings(pageable);
    }

    @GetMapping("all/{date}")
    public List<Booking> getBookingsByDate(@PathVariable Date date, Pageable pageable){
        return service.findAllBookingsByDate(date, pageable);
    }

    @GetMapping("all/{date1}/{date2}")
    public List<Booking> getBookingsByRange(@PathVariable Date date1, @PathVariable Date date2, Pageable pageable){
        return service.findAllBookingsByRange(date1, date2, pageable);
    }

    @GetMapping("checkin/{date}")
    public List<Booking> getBookingsByCheckin(@PathVariable Date date, Pageable pageable){
        return service.findAllBookingsByCheckin(date, pageable);
    }

    @GetMapping("checkout/{date}")
    public List<Booking> getBookingsByCheckout(@PathVariable Date date, Pageable pageable){
        return service.findAllBookingsByCheckout(date, pageable);
    }

    @GetMapping("people/{people}")
    public List<Booking> getBookingsByPeople(@PathVariable int people, Pageable pageable){
        return service.findAllBookingsByPeople(people, pageable);
    }

    @GetMapping("room/{id}")
    public List<Booking> getBookingsByRoom(@PathVariable int number, Pageable pageable){
        return service.findAllBookingsByRoom(number, pageable);
    }

    @GetMapping("guest/{id}")
    public List<Booking> getBookingsByGuest(@PathVariable int id, Pageable pageable){
        return service.findAllBookingsByGuest(id, pageable);
    }

    @GetMapping("{id}")
    public Booking getBookingById(@PathVariable int id){ return service.findByBookingId(id); }

    @PutMapping(consumes = {"application/xml","application/json"})
    public Booking insertBooking(@RequestBody Booking booking){
        return service.saveBooking(booking);
    }

    @PostMapping(consumes = {"application/xml","application/json"})
    public Booking updateBooking(@RequestBody Booking booking){ return service.updateBooking(booking); }

    @DeleteMapping("{id}")
    public boolean deleteBookingById(@PathVariable int id){ return service.deleteBooking(id); }

}
