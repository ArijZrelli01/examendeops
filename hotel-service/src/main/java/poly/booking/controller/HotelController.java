package poly.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import poly.booking.entities.Hotel;
import poly.booking.repository.HotelRepository;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class HotelController {
    private final HotelRepository repository;

    @GetMapping
    public List<Hotel> getAllHotels() {
        return repository.findAll();
    }

    @PostMapping
    public Hotel addHotel(@RequestBody Hotel hotel) {
        return repository.save(hotel);
    }
}
