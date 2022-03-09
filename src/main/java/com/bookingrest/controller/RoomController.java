package com.bookingrest.controller;

import com.bookingrest.model.Room;
import com.bookingrest.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/room")
public class RoomController {

    RoomService service;

    @Autowired
    public RoomController(RoomService service){
        this.service = service;
    }

    @GetMapping("all")
    public List<Room> getRooms(Pageable pageable){
        return service.findAllRooms(pageable);
    }

    @GetMapping("all/booked")
    public List<Room> getAllBookedRooms(Pageable pageable){
        return service.findAllBookedRooms(pageable);
    }

    @GetMapping("all/booked/{date}")
    public List<Room> getBookedRoomsByDate(@PathVariable Date date, Pageable pageable){
        return service.findAllBookedRoomsByDate(date, pageable);
    }

    @GetMapping("all/booked/{date1}/{date2}")
    public List<Room> getBookedRoomsByRange(@PathVariable Date date1, @PathVariable Date date2, Pageable pageable){
        return service.findAllBookedRoomsByRange(date1, date2, pageable);
    }

    @GetMapping("all/free")
    public List<Room> getFreeRooms(Pageable pageable){ return service.findAllFreeRooms(pageable); }

    @GetMapping("all/free/{date}")
    public List<Room> getFreeRoomsByDate(@PathVariable Date date, Pageable pageable){
        return service.findAllFreeRoomsByDate(date, pageable);
    }

    @GetMapping("all/free/{date1}/{date2}")
    public List<Room> getFreeRoomsByRange(@PathVariable Date date1, @PathVariable Date date2, Pageable pageable){
        return service.findAllFreeRoomsByRange(date1, date2, pageable);
    }

    @GetMapping("{number}")
    public Room getRoomByNumber(@PathVariable int number){ return service.findByRoomNumber(number); }

    @GetMapping("floor/{floor}")
    public List<Room> getRoomsByFloor(@PathVariable int floor, Pageable pageable){
        return service.findAllRoomsByFloor(floor, pageable);
    }

    @GetMapping("capacity/{capacity}")
    public List<Room> getRoomsByCapacity(@PathVariable int capacity, Pageable pageable){
        return service.findAllRoomsByCapacity(capacity, pageable);
    }

    @GetMapping("type/{id}")
    public List<Room> getRoomsByType(@PathVariable int id, Pageable pageable){
        return service.findAllRoomsByType(id, pageable);
    }

    @PutMapping(consumes = {"application/xml","application/json"})
    public Room insertRoom(@RequestBody Room room){
        return service.saveRoom(room);
    }

    @PostMapping(consumes = {"application/xml","application/json"})
    public Room updateRoom(@RequestBody Room room){ return service.updateRoom(room); }

    @DeleteMapping("{number}")
    public boolean deleteRoomByNumber(@PathVariable int number){ return service.deleteRoom(number); }

}
