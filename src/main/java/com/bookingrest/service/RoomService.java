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

import java.util.Date;
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

    public List<Room> findAllRoomsByType(int typeId, Pageable pageable){
        return repository.findAllByType(service.findByTypeId(typeId),
                BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Room> findAllBookedRooms(Pageable pageable){
        return repository.findAllBookedRooms(BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Room> findAllBookedRoomsByDate(Date date, Pageable pageable){
        return repository.findAllBookedRoomsByRange(date, date,
                BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Room> findAllBookedRoomsByRange(Date date1, Date date2, Pageable pageable){
        return repository.findAllBookedRoomsByRange(
                BookingUtil.newer(date1, date2), BookingUtil.older(date1, date2),
                BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Room> findAllFreeRooms(Pageable pageable){
        Date date = new Date(System.currentTimeMillis());
        return repository.findAllFreeRoomsByRange(date, date, BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Room> findAllFreeRoomsByDate(Date date, Pageable pageable){
        return repository.findAllFreeRoomsByRange(date, date, BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Room> findAllFreeRoomsByRange(Date date1, Date date2, Pageable pageable){
        return repository.findAllFreeRoomsByRange(
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
