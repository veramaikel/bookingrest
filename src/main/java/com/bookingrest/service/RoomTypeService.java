package com.bookingrest.service;

import com.bookingrest.model.RoomType;
import com.bookingrest.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomTypeService {

    RoomTypeRepository repository;
    Sort defaultSort;

    @Autowired
    public RoomTypeService(RoomTypeRepository repository) {
        this.repository = repository;
        defaultSort = Sort.by("name");
    }

    public List<RoomType> findAllTypes(Pageable pageable){
        return repository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), defaultSort))
                .getContent();
    }

    public List<RoomType> findAllTypesByName(String name, Pageable pageable){
        return repository.findAllByNameContains(name,
                        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), defaultSort))
                                .getContent();
    }

    public RoomType findByTypeId(int id){
        return repository.findById(id);
    }

    public RoomType findByTypeName(String name){
        return repository.findByName(name);
    }

    @Transactional
    public RoomType saveType(RoomType type){
        return repository.save(type);
    }

    @Transactional
    public RoomType updateType(RoomType type){
        return repository.save(type);
    }

    @Transactional
    public boolean deleteType(int id) {
        try{
            repository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Transactional
    public boolean deleteType(String name) {
        try{
            RoomType type = repository.findByName(name);
            repository.delete(type);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
