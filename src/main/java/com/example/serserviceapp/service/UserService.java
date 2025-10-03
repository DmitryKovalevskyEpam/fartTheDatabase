package com.example.serserviceapp.service;

import com.example.serserviceapp.model.entity.User;
import com.example.serserviceapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser (User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("User  with email " + user.getEmail() + " already exists");
        }
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<User> getUsersByIds(List<Long> ids) {
        return userRepository.findByIds(ids);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<User> getUsersByNameAndSurname(String name, String surname) {
        return userRepository.findByNameAndSurname(name, surname);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUser (Long id, String name, String surname, String email, LocalDate birthDate) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User  with id " + id + " not found");
        }
        userRepository.updateUser (id, name, surname, email, birthDate);
    }

    public void updateUserName(Long id, String name, String surname) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User  with id " + id + " not found");
        }
        userRepository.updateFullNameById(id, name, surname);
    }

    public User updateUser (User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new RuntimeException("User  with id " + user.getId() + " not found");
        }
        return userRepository.save(user);
    }

    public void deleteUser (Long id) {
        userRepository.deleteById(id);
    }

    public boolean userExists(Long id) {
        return userRepository.existsById(id);
    }
}