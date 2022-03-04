package com.bookingrest.service;

import com.bookingrest.model.Country;
import com.bookingrest.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    CountryRepository repository;

    @Autowired
    public CountryService(CountryRepository repository) { this.repository = repository; }

    public List<Country> findAllCountries(Pageable pageable){
        return repository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name")))
                .getContent();
    }

    public List<Country> findAllCountriesByName(String name, Pageable pageable){
        return repository.findAllByName(name,
                        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name")))
                                .getContent();
    }

    public Country findCountryById(int id){
        return repository.findById(id);
    }

    public Country findCountryByName(String name){
        return repository.findByName(name);
    }

    public Country saveCountry(Country country){
        return repository.save(country);
    }

    public Country updateCountry(Country country){
        return repository.save(country);
    }

    public boolean deleteCountry(int id) {
        try{
            repository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean deleteCountry(String name) {
        try{
            Country country = repository.findByName(name);
            repository.delete(country);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
