package org.example.service;

import java.util.Map;

public interface UserService {
    String createUser(Map userDetails);
    Map<String, String> updateUser(String userId, Map userDetails);
    Map<String, String> getUserDetails(String userId);
    void deleteUser(String userId);
}
