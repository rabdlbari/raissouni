package com.bookshop.service;

import com.bookshop.dto.UserDto;
import com.bookshop.entity.User;
import com.bookshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<UserDto> getAllUsers() {
        return userRepo.findAll().stream().map(this::toDto).toList();
    }

    public UserDto createUser(User user) {
        return toDto(userRepo.save(user));
    }

    public UserDto toDto(User user){
        return new UserDto(user.getId(), user.getEmail());
    }
}