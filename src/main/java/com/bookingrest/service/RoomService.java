package com.bookingrest.service;

import com.bookingrest.model.Room;
import com.bookingrest.model.RoomType;
import com.bookingrest.repository.RoomRepository;
import com.bookingrest.util.BookingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService {

    RoomRepository repository;
    RoomTypeService service;
    Sort defaultSort;

    @Autowired
    public RoomService(RoomRepository repository, RoomTypeService service) {
        this.repository = repository;
        this.service = service;
        defaultSort = Sort.by("number").and(Sort.by("floor"));
    }

    public List<Room> findAllRooms(Pageable pageable){
        return repository.findAll(BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Room> findAllRoomsByFloor(int floor, Pageable pageable){
        return repository.findAllByFloor(floor, BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Room> findAllRoomsByCapacity(int capacity, Pageable pageable){
        return repository.findAllByCapacity(capacity, BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Room> findAllRoomsByPrice(BigDecimal price, Pageable pageable){
        return repository.findAllByPrice(price, BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Room> findAllRoomsByType(int typeId, Pageable pageable){
        return repository.findAllByType(service.findByTypeId(typeId),
                BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Room> findAllBookedRooms(Pageable pageable){
        return repository.findAllBookedRooms(BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Room> findAllBookedRoomsToday(Pageable pageable){
        LocalDate date = LocalDate.now();
        return repository.findAllBookedRoomsByRange(date, date,
                BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Room> findAllBookedRoomsByDate(LocalDate date, Pageable pageable){
        return repository.findAllBookedRoomsByRange(date, date,
                BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Room> findAllBookedRoomsByRange(LocalDate date1, LocalDate date2, Pageable pageable){
        return repository.findAllBookedRoomsByRange(
                BookingUtil.newer(date1, date2), BookingUtil.older(date1, date2),
                BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Room> findAllFreeRoomsToday(Pageable pageable){
        LocalDate date = LocalDate.now();
        return repository.findAllFreeRoomsByRange(date, date,
                BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Room> findAllFreeRoomsByDate(LocalDate date, Pageable pageable){
        return repository.findAllFreeRoomsByRange(date, date,
                BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Room> findAllFreeRoomsByRange(LocalDate date1, LocalDate date2, Pageable pageable){
        return repository.findAllFreeRoomsByRange(
                BookingUtil.newer(date1, date2), BookingUtil.older(date1, date2),
                BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Room> findAllCheapestFreeRoomsToday(Pageable pageable){
        LocalDate date = LocalDate.now();
        return repository.findAllCheapestFreeRoomsByRange(date, date,
                BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Room> findAllCheapestFreeRoomsByDate(LocalDate date, Pageable pageable){
        return repository.findAllCheapestFreeRoomsByRange(date, date,
                BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Room> findAllCheapestFreeRoomsByRange(LocalDate date1, LocalDate date2, Pageable pageable){
        return repository.findAllCheapestFreeRoomsByRange(
                BookingUtil.newer(date1, date2), BookingUtil.older(date1, date2),
                BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public Room findByRoomNumber(int number){
        return repository.findByNumber(number);
    }

    @Transactional
    public Room saveRoom(Room room){
        return repository.save(setPersistent(room));
    }

    @Transactional
    public Room updateRoom(Room room){
        return repository.save(setPersistent(room));
    }

    private Room setPersistent(Room room){
        RoomType type = room.getType();
        if(type.getId()!=null) {
            type = service.findByTypeId(type.getId());
            room.setType(type);
        }
        else if(type.getName()!=null) {
            type = service.findByTypeName(type.getName());
            if(type!=null) room.setType(type);
        }
        return room;
    }

    @Transactional
    public boolean deleteRoom(int number) {
        try{
            repository.deleteById(number);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
