package com.fartone.cardservice;

import com.fartone.cardservice.entity.CardInfo;
import com.fartone.cardservice.entity.User;
import com.fartone.cardservice.dao.UserRepository;
import com.fartone.cardservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void whenGetUserById_shouldReturnUser() {
        User user = new User();
        user.setId(1L);
        user.setName("John");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserById(1L);

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("John");
    }

    @Test
    void whenCreateUser_shouldReturnSavedUser() {
        User user = new User();
        user.setName("Jane");
        user.setEmail("jane.doe@example.com");
        user.setBirthDate(LocalDate.of(1990, 5, 15));
        user.setSurname("Doe");

        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getName()).isEqualTo("Jane");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void whenDeleteUser_shouldReturnTrueForExistingUser() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        boolean isDeleted = userService.deleteUser(1L);

        assertThat(isDeleted).isTrue();
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void whenDeleteUser_shouldReturnFalseForNonExistingUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        boolean isDeleted = userService.deleteUser(1L);

        assertThat(isDeleted).isFalse();
        verify(userRepository, never()).delete(any());
    }
}
