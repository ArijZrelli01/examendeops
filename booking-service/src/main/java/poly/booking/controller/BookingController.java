package poly.booking.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import poly.booking.entities.Booking;
import poly.booking.repository.BookingRepository;
import poly.booking.feign.HotelRestClient;
import poly.booking.feign.ClientRestClient;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class BookingController {
    private final BookingRepository repository;
    private final HotelRestClient hotelClient;
    private final ClientRestClient client;

    @PostMapping
    public Booking create(@RequestBody Booking booking) {
        return repository.save(booking);
    }




    @GetMapping
    public List<Map<String, Object>> getBookings(
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String date) {

        List<Booking> bookings = repository.findAll();

        // Filtrer par téléphone
        if (phone != null && !phone.isEmpty()) {
            bookings = bookings.stream()
                    .filter(b -> phone.equals(b.getPhone()))
                    .collect(Collectors.toList());
        }


// Filtrer par date d'entrée (startDate) exactement égale à filterDate
        if (date != null && !date.isEmpty()) {
            LocalDate filterDate = LocalDate.parse(date);
            bookings = bookings.stream()
                    .filter(b -> b.getStartDate().isEqual(filterDate)) // startDate == filterDate
                    .collect(Collectors.toList());
        }

        // Construire le résultat avec les infos client et hôtel
        return bookings.stream().map(b -> {
            Map<String, Object> map = new HashMap<>();
            map.put("booking", b);
            map.put("customer", client.getClientByPhone(b.getPhone())); // objet client avec fullName et phone
//            map.put("hotel", hotelClient.getHotelById(b.getHotelId())); // objet hotel avec name

            // Récupérer le premier hôtel ou null si la liste est vide
            List<Map<String, Object>> hotels = hotelClient.getHotelById(b.getHotelId());
            map.put("hotel", (hotels != null && !hotels.isEmpty()) ? hotels.get(0) : null);
            System.out.println("Mapping booking: " + map); // <-- log côté backend

            return map;
        }).collect(Collectors.toList());
    }

}
