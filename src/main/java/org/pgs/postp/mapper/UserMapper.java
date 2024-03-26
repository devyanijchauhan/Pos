package org.pgs.postp.mapper;

import org.pgs.postp.dto.UserDTO;
import org.pgs.postp.model.ProductModel;
import org.pgs.postp.model.UserModel;
import org.pgs.postp.model.RoleModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // Convert UserModel to UserDTO
    public UserDTO toDTO(UserModel model) {
        UserDTO dto = new UserDTO();
        dto.setUserID(model.getUserID());
        dto.setUsername(model.getUsername());
        dto.setPassword(model.getPassword());
        dto.setName(model.getName());
        dto.setEmail(model.getEmail());
        dto.setPhone(model.getPhone());
        if (model.getRole() != null) {
            dto.setRoleId(model.getRole().getRoleID());
        }
        // You might choose to map other properties here, depending on your requirements
        return dto;
    }

    // Convert UserDTO to UserModel
    public static UserModel toEntity(UserDTO dto) {
        UserModel model = new UserModel();
        model.setUserID(dto.getUserID());
        model.setUsername(dto.getUsername());
        model.setPassword(dto.getPassword());
        model.setName(dto.getName());
        model.setEmail(dto.getEmail());
        model.setPhone(dto.getPhone());
        if (dto.getRoleId() != null) {
            RoleModel role = new RoleModel();
            role.setRoleID(dto.getRoleId());
            model.setRole(role);
        }
        // You might choose to map other properties here, depending on your requirements
        return model;
    }
}
