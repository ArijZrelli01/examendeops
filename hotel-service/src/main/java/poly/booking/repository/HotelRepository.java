package poly.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import poly.booking.entities.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {


}

