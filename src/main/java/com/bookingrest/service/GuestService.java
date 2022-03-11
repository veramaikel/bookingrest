package com.bookingrest.service;

import com.bookingrest.model.Country;
import com.bookingrest.model.Guest;
import com.bookingrest.repository.GuestRepository;
import com.bookingrest.util.BookingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class GuestService {

    GuestRepository repository;
    CountryService service;
    Sort defaultSort;

    @Autowired
    public GuestService(GuestRepository repository, CountryService service) {
        this.repository = repository;
        this.service = service;
        defaultSort = Sort.by("name");
    }

    public List<Guest> findAllGuests(Pageable pageable){
        return repository.findAll(BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Guest> findAllGuestsByName(String name, Pageable pageable){
        return repository.findAllByNameContains(name, BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Guest> findAllGuestsByCountry(int countryId, Pageable pageable){
        return repository.findAllByCountry(service.findByCountryId(countryId),
                BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Guest> findAllGuestsHaveBooked(Pageable pageable){
        return repository.findAllHaveBooked(BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Guest> findAllGuestsHaveBookedToday(Pageable pageable){
        Date date = new Date(System.currentTimeMillis());
        return repository.findAllHaveBookedByRange(date, date,
                BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Guest> findAllGuestsHaveBookedByDate(Date date, Pageable pageable){
        return repository.findAllHaveBookedByRange(date, date,
                BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public List<Guest> findAllGuestsHaveBookedByRange(Date date1, Date date2, Pageable pageable){
        return repository.findAllHaveBookedByRange(
                BookingUtil.newer(date1, date2), BookingUtil.older(date1, date2),
                BookingUtil.getPageable(pageable, defaultSort)).getContent();
    }

    public Guest findByGuestId(int id){
        return repository.findById(id);
    }

    public Guest findByGuestName(String name){
        return repository.findByName(name);
    }

    @Transactional
    public Guest saveGuest(Guest guest){
        return repository.save(setPersistent(guest));
    }

    @Transactional
    public Guest updateGuest(Guest guest){
        return repository.save(setPersistent(guest));
    }

    private Guest setPersistent(Guest guest){

        Country country = guest.getCountry();
        //System.out.println("before :" +country);
        if(country.getId()!=null) country = service.findByCountryId(country.getId());
        else country.addGuest(guest);
        //System.out.println("after :" +country);
        guest.setCountry(country);
        return guest;
    }

    @Transactional
    public boolean deleteGuest(int id) {
        try{
            repository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
