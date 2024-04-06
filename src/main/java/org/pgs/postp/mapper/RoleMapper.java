package org.pgs.postp.mapper;

import org.pgs.postp.dto.RoleDTO;
import org.pgs.postp.model.RoleModel;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {


    public RoleDTO toDTO(RoleModel model) {
        RoleDTO dto = new RoleDTO();
        dto.setRoleID(model.getRoleID());
        dto.setRoleName(model.getRoleName());

        return dto;
    }


    public RoleModel toEntity(RoleDTO dto) {
        RoleModel model = new RoleModel();
        model.setRoleID(dto.getRoleID());
        model.setRoleName(dto.getRoleName());

        return model;
    }
}
