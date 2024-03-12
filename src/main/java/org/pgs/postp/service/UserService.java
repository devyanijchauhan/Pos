package org.pgs.postp.service;

import org.pgs.postp.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(Long id, UserDTO userDTO);

    void deleteUser(Long id);
}
