package com.bookingrest.service;

import com.bookingrest.model.Country;
import com.bookingrest.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CountryService {

    CountryRepository repository;
    Sort defaultSort;

    @Autowired
    public CountryService(CountryRepository repository) {
        this.repository = repository;
        defaultSort = Sort.by("name");
    }

    public List<Country> findAllCountries(Pageable pageable){
        return repository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), defaultSort))
                .getContent();
    }

    public List<Country> findAllCountriesByName(String name, Pageable pageable){
        return repository.findAllByNameContains(name,
                        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), defaultSort))
                                .getContent();
    }

    public Country findByCountryId(int id){
        return repository.findById(id);
    }

    public Country findByCountryName(String name){
        return repository.findByName(name);
    }

    @Transactional
    public Country saveCountry(Country country){
        return repository.save(country);
    }

    @Transactional
    public Country updateCountry(Country country){
        return repository.save(country);
    }

    @Transactional
    public boolean deleteCountry(int id) {
        try{
            repository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Transactional
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
