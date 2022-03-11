package com.bookingrest.controller;

import com.bookingrest.model.Guest;
import com.bookingrest.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/guest")
public class GuestController {

    GuestService service;

    @Autowired
    public GuestController(GuestService service){
        this.service = service;
    }

    @GetMapping("all")
    public List<Guest> getGuests(Pageable pageable){
        return service.findAllGuests(pageable);
    }

    @GetMapping("all/booked/total")
    public List<Guest> getGuestsHaveBooked(Pageable pageable){
        return service.findAllGuestsHaveBooked(pageable);
    }

    @GetMapping("all/booked")
    public List<Guest> getGuestsHaveBookedToday(Pageable pageable){
        return service.findAllGuestsHaveBookedToday(pageable);
    }

    @GetMapping("all/booked/{date}")
    public List<Guest> getGuestsHaveBookedByDate(@PathVariable Date date, Pageable pageable){
        return service.findAllGuestsHaveBookedByDate(date, pageable);
    }

    @GetMapping("all/booked/{date1}/{date2}")
    public List<Guest> getGuestsHaveBookedByRange(@PathVariable Date date1,
                                                        @PathVariable Date date2, Pageable pageable){
        return service.findAllGuestsHaveBookedByRange(date1, date2, pageable);
    }

    @GetMapping("all/{name}")
    public List<Guest> getGuestsByName(@PathVariable String name, Pageable pageable){
        return service.findAllGuestsByName(name, pageable);
    }

    @GetMapping("country/{id}")
    public List<Guest> getGuestsByCountry(@PathVariable int id, Pageable pageable){
        return service.findAllGuestsByCountry(id, pageable);
    }

    @GetMapping("{id}")
    public Guest getGuestById(@PathVariable int id){ return service.findByGuestId(id); }

    @GetMapping("name/{name}")
    public Guest getGuestByName(@PathVariable String name){ return service.findByGuestName(name); }

    @PutMapping(consumes = {"application/xml","application/json"})
    public Guest insertGuest(@RequestBody Guest guest){
        return service.saveGuest(guest);
    }

    @PostMapping(consumes = {"application/xml","application/json"})
    public Guest updateRoom(@RequestBody Guest guest){ return service.updateGuest(guest); }

    @DeleteMapping("{id}")
    public boolean deleteGuestById(@PathVariable int id){ return service.deleteGuest(id); }

}
