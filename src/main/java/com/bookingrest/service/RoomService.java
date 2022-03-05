package com.bookingrest.service;

import com.bookingrest.model.Room;
import com.bookingrest.model.RoomType;
import com.bookingrest.repository.RoomRepository;
import com.bookingrest.util.DateComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
        defaultSort = Sort.by("floor").and(Sort.by("number"));
    }

    public List<Room> findAllRooms(Pageable pageable){
        return repository.findAll(
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), defaultSort)).getContent();
    }

    public List<Room> findAllRoomsByFloor(int floor, Pageable pageable){
        return repository.findAllByFloor(floor,
                        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), defaultSort))
                                .getContent();
    }

    public List<Room> findAllRoomsByCapacity(int capacity, Pageable pageable){
        return repository.findAllByCapacity(capacity,
                        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), defaultSort))
                .getContent();
    }

    public List<Room> findAllRoomsByType(int typeId, Pageable pageable){
        return repository.findAllByType(service.findTypeById(typeId),
                        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), defaultSort))
                .getContent();
    }

    public List<Room> findAllReservedRooms(Pageable pageable){
        return repository.getAllReservedRooms(
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), defaultSort))
                .getContent();
    }

    public List<Room> findAllReservedRoomsByDate(Date date, Pageable pageable){
        return repository.getReservedRoomsByRange(date, date,
                        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), defaultSort))
                .getContent();
    }

    public List<Room> findAllReservedRoomsByRange(Date date1, Date date2, Pageable pageable){
        return repository.getReservedRoomsByRange(
                DateComparator.newer(date1, date2), DateComparator.older(date1, date2),
                        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), defaultSort))
                .getContent();
    }

    public List<Room> findAllFreeRoomsByDate(Date date, Pageable pageable){
        return repository.getFreeRoomsByRange(date, date,
                        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), defaultSort))
                .getContent();
    }

    public List<Room> findAllFreeRoomsByRange(Date date1, Date date2, Pageable pageable){
        return repository.getFreeRoomsByRange(
                DateComparator.newer(date1, date2), DateComparator.older(date1, date2),
                        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), defaultSort))
                .getContent();
    }

    public Room findRoomByNumber(Integer number){
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
            type = service.findTypeById(type.getId());
            room.setType(type);
        }
        return room;
    }

    @Transactional
    public boolean deleteRoom(Integer number) {
        try{
            repository.deleteById(number);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
