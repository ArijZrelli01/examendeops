package poly.booking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import poly.booking.entities.Client;
import poly.booking.repository.ClientRepository;

@SpringBootApplication
 public class ClientServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }



//    @Bean
//    CommandLineRunner start(ClientRepository clientRepository) {
//        return args -> {
//            clientRepository.save(new Client(null ,"Arij", "arij@gmail.com","99666333" ));
//            clientRepository.save(new Client( null ,"Aya", "aya@gmail.com","22333444"));
//            clientRepository.save(new Client(null , "Nour", "nour@gmail.com","55222333"));
//        };
//    }
}