package com.learnhub.backend.user.service;

import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.user.dto.request.UpdateProfileRequest;
import com.learnhub.backend.user.dto.response.ProfileResponse;
import com.learnhub.backend.user.entity.User;
import com.learnhub.backend.user.repository.UserRepository;
import com.learnhub.backend.user.service.impl.ProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProfileServiceImpl profileService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setName("Riya Raj");
        sampleUser.setEmail("riya@learnhub.com");
        sampleUser.setRole("LEARNER");
        sampleUser.setHeadline("Full Stack Learner");
        sampleUser.setLocation("Bangalore");
        sampleUser.setAvatarUrl("https://example.com/avatar.jpg");
    }

    @Test
    @DisplayName("Should return ProfileResponse when user exists")
    void getProfile_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

        ProfileResponse response = profileService.getProfile(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Riya Raj", response.getName());
        assertEquals("riya@learnhub.com", response.getEmail());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when user does not exist")
    void getProfile_NotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> profileService.getProfile(99L));
        verify(userRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Should update user profile successfully")
    void updateProfile_Success() {
        UpdateProfileRequest request = UpdateProfileRequest.builder()
                .name("Riya Raj Updated")
                .headline("Senior Developer Learner")
                .location("Mumbai")
                .avatarUrl("https://example.com/new_avatar.jpg")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
        when(userRepository.save(any(User.class))).thenReturn(sampleUser);

        ProfileResponse response = profileService.updateProfile(1L, request);

        assertNotNull(response);
        assertEquals("Riya Raj Updated", response.getName());
        assertEquals("Senior Developer Learner", response.getHeadline());
        assertEquals("Mumbai", response.getLocation());
        verify(userRepository, times(1)).save(sampleUser);
    }
}
