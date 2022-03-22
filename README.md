# BookingREST Project-1
Hotel Booking System

## Description
A RESTful API of a Hotel Booking System where through the API endpoints we can manage the necessary information to configure and start up a room reservation system in a hotel. After loading the information of the room types and the data of the rooms we will be able to relate guests with rooms through bookings. It is possible to consult the free rooms for a certain date and even the most economical depending on the number of people to stay.

## Technology Used
* Java, Maven
* Spring Boot, Spring Data, Spring AOP, Spring Framework
* Azure SQL Databases, Azure Virtual Machine
* Hibernate, Hibernate Validator
* Log4j, Lombok, Postman

## Features
### Rooms
* CRUD operations for RoomType
* CRUD operations for Room
* Search the Rooms that are reserved, all, by a date, and by a range of dates
* Search the Rooms that are free, today, by a date, and by a range of date
* Search the Rooms that are free and are the cheapest, by a date, and by a range of dates
* Search the Rooms by capacity, price, floor, type, and id
### Guests
* CRUD operations for Country
* CRUD operations for Guest
* Search for Guests today, or on a particular date or in a range of dates
* Search the Guests by country, name, and id
### Booking
* Search for Bookings today, or on a particular date or in a range of dates
* Search for Bookings by guest, or by room, or on a particular date or in a range of dates
* Create a Booking by guest, check-in date nad room (Insert)
* Cancel Booking (Delete)
* Search for open Bookings, these are the ones that have an check-in date but no check-out
