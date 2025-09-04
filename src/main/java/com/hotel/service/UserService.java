package com.hotel.service;

import com.hotel.entity.User;
import java.util.List;

public interface UserService {
    User getUserById(String id);
    List<User> getAllUsers();
    User updateUser(String id, User userDetails);
    void deleteUser(String id);
}
