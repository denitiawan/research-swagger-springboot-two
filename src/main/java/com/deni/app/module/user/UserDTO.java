package com.deni.app.module.user;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    Long id;
    String username;
    String password;
    String roles = "";
    String permissions = "";
    Integer blocked;
    Integer active;


    String createdBy;
    Date createdDate;
    String updatedBy;
    Date updatedDate;


    public List<UserDTO> listOf(List<User> list) {
        List<UserDTO> result = new ArrayList<>();
        for (User entity : list) {
            result.add(of(entity));
        }
        return result;
    }

    public UserDTO of(User entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .permissions(entity.getPermissions())
                .roles(entity.getRoles())
                .active(entity.getActive())
                .blocked(entity.getBlocked())
                .createdBy(entity.getCreatedBy())
                .createdDate(entity.getCreatedDate())
                .updatedBy(entity.getUpdatedBy())
                .updatedDate(entity.getUpdatedDate())
                .build();
    }


    public List<UserDTO> listOfWithHidePassword(List<User> list) {
        List<UserDTO> result = new ArrayList<>();
        for (User entity : list) {
            result.add(showDataWithHidePassword(entity));
        }
        return result;
    }
    public UserDTO showDataWithHidePassword(User entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password("*****")
                .permissions(entity.getPermissions())
                .roles(entity.getRoles())
                .active(entity.getActive())
                .blocked(entity.getBlocked())
                .createdBy(entity.getCreatedBy())
                .createdDate(entity.getCreatedDate())
                .updatedBy(entity.getUpdatedBy())
                .updatedDate(entity.getUpdatedDate())
                .build();
    }
}
