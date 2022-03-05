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

    @GetMapping("all/reserved")
    public List<Room> getAllReservedRooms(Pageable pageable){
        return service.findAllReservedRooms(pageable);
    }

    @GetMapping("all/reserved/{date}")
    public List<Room> getReservedRoomsByDate(@PathVariable Date date, Pageable pageable){
        return service.findAllReservedRoomsByDate(date, pageable);
    }

    @GetMapping("all/reserved/{date1}/{date2}")
    public List<Room> getReservedRoomsByRange(@PathVariable Date date1, @PathVariable Date date2, Pageable pageable){
        return service.findAllReservedRoomsByRange(date1, date2, pageable);
    }

    @GetMapping("all/free/{date}")
    public List<Room> getFreeRoomsByDate(@PathVariable Date date, Pageable pageable){
        return service.findAllFreeRoomsByDate(date, pageable);
    }

    @GetMapping("all/free/{date1}/{date2}")
    public List<Room> getFreeRoomsByRange(@PathVariable Date date1, @PathVariable Date date2, Pageable pageable){
        return service.findAllFreeRoomsByRange(date1, date2, pageable);
    }

    @GetMapping("{id}")
    public Room getRoomById(@PathVariable int id){ return service.findRoomById(id); }

    @GetMapping("{number}/{floor}")
    public Room getRoomByNumberAndFloor(@PathVariable int number, @PathVariable int floor){
        return service.findRoomByNumberAndFloor(number, floor);
    }

    @GetMapping("floor/{floor}")
    public List<Room> getRoomsByFloor(@PathVariable int floor, Pageable pageable){
        return service.findAllRoomsByFloor(floor, pageable);
    }

    @GetMapping("capacity/{capacity}")
    public List<Room> getRoomsByCapacity(@PathVariable int capacity, Pageable pageable){
        return service.findAllRoomsByCapacity(capacity, pageable);
    }

    @GetMapping("type/{typeId}")
    public List<Room> getRoomsByType(@PathVariable int typeId, Pageable pageable){
        return service.findAllRoomsByType(typeId, pageable);
    }

    @PutMapping(consumes = {"application/xml","application/json"})
    public Room insertRoom(@RequestBody Room room){
        return service.saveRoom(room);
    }

    @PostMapping(consumes = {"application/xml","application/json"})
    public Room updateRoom(@RequestBody Room room){ return service.updateRoom(room); }

    @DeleteMapping("{id}")
    public boolean deleteRoomById(@PathVariable int id){ return service.deleteRoom(id); }

    @DeleteMapping("{number}/{floor}")
    public boolean deleteRoomByNumberAndFloor(@PathVariable int number, @PathVariable int floor){
        return service.deleteRoom(number, floor);
    }
}
