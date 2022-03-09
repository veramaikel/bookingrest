package com.bookingrest.controller;

import com.bookingrest.model.RoomType;
import com.bookingrest.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/type")
public class RoomTypeController {

    RoomTypeService service;

    @Autowired
    public RoomTypeController(RoomTypeService service){ this.service = service; }

    @GetMapping("all")
    public List<RoomType> getTypes(Pageable pageable){
        return service.findAllTypes(pageable);
    }

    @GetMapping("{id}")
    public RoomType getTypeById(@PathVariable int id){ return service.findByTypeId(id); }

    @GetMapping("name/{name}")
    public RoomType getTypeByName(@PathVariable String name){ return service.findByTypeName(name); }

    @GetMapping("all/{name}")
    public List<RoomType> getTypesByName(@PathVariable String name, Pageable pageable){
        return service.findAllTypesByName(name, pageable);
    }

    @PutMapping
    public RoomType insertType(@RequestBody RoomType type){
        return service.saveType(type);
    }

    @PostMapping
    public RoomType updateType(@RequestBody RoomType type){ return service.updateType(type); }

    @DeleteMapping("{id}")
    public boolean deleteTypeById(@PathVariable int id){ return service.deleteType(id); }

    @DeleteMapping("name/{name}")
    public boolean deleteTypeByName(@PathVariable String name){ return service.deleteType(name); }
}
