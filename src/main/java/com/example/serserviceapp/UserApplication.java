package com.example.serserviceapp;

import com.example.serserviceapp.model.entity.CardInfo;
import com.example.serserviceapp.model.entity.User;
import com.example.serserviceapp.repository.CardInfoRepository;
import com.example.serserviceapp.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.serserviceapp.repository")
public class UserApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(UserApplication.class, args);

        UserRepository userRepository = context.getBean(UserRepository.class);
        CardInfoRepository cardInfoRepository = context.getBean(CardInfoRepository.class);

        User user = new User("Anna", "Koval", LocalDate.of(1990, 1, 1), "anna@example.com");
        CardInfo card = new CardInfo(user, "1234-5678", "Anna Koval", LocalDate.of(2025, 12, 31));
        user.addCard(card);

        User savedUser  = userRepository.save(user);

        Optional<User> foundUser  = userRepository.findById(savedUser .getId());

        List<User> usersByIds = userRepository.findByIdIn(List.of(savedUser .getId()));

        Optional<User> byEmail = userRepository.findByEmail("anna@example.com");

        userRepository.updateNameAndSurnameById(savedUser .getId(), "Max", "Koval");

        userRepository.deleteById(savedUser .getId());
    }
}