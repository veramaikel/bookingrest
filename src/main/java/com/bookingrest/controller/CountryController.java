package com.bookingrest.controller;

import com.bookingrest.model.Country;
import com.bookingrest.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/country")
public class CountryController {

    CountryService service;

    @Autowired
    public CountryController(CountryService service){ this.service = service; }

    @GetMapping("all")
    public List<Country> getCountries(Pageable pageable){
        return service.findAllCountries(pageable);
    }

    @GetMapping("{id}")
    public Country getCountryById(@PathVariable int id){ return service.findCountryById(id); }

    @GetMapping("name/{name}")
    public Country getCountryByName(@PathVariable String name){ return service.findCountryByName(name); }

    @GetMapping("all/{name}")
    public List<Country> getCountriesByName(@PathVariable String name, Pageable pageable){
        return service.findAllCountriesByName(name, pageable);
    }

    @PutMapping
    public Country putCountry(@RequestBody Country country){
        return service.saveCountry(country);
    }

    @PostMapping
    public Country postCountry(@RequestBody Country country){ return service.updateCountry(country); }

    @DeleteMapping("{id}")
    public boolean deleteCountryById(@PathVariable int id){ return service.deleteCountry(id); }

    @DeleteMapping("name/{name}")
    public boolean deleteCountryByName(@PathVariable String name){ return service.deleteCountry(name); }
}
