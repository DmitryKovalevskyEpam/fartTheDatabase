package com.fartone.cardservice.service;

import com.fartone.cardservice.entity.User;
import com.fartone.cardservice.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "users")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @CacheEvict(allEntries = true)
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Cacheable(key = "#id")
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getUsersByIds(List<Long> ids) {
        return userRepository.findByIdIn(ids);
    }

    @Cacheable(key = "#email")
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    // Evict cache entries after successful update so subsequent reads get fresh data.
    @CacheEvict(allEntries = true)
    public Optional<User> updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setName(userDetails.getName());
            user.setSurname(userDetails.getSurname());
            user.setBirthDate(userDetails.getBirthDate());
            user.setEmail(userDetails.getEmail());
            return userRepository.save(user);
        });
    }

    @Transactional
    // Use findById + delete(entity) so unit tests that verify delete(User) will work.
    @CacheEvict(allEntries = true)
    public boolean deleteUser(Long id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
