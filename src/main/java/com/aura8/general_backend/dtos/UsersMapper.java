package com.aura8.general_backend.dtos;

import com.aura8.general_backend.entities.Users;

import java.time.LocalDateTime;

public class UsersMapper {

    public static Users toEntity(UsersRegisterDto dto){

        if (dto == null) return null;

        Users user = new Users();

        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setUsername(dto.getUsername());
        user.setPhone(dto.getPhone());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setDeleted(false);
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setModifiedAt(now);

        return user;
    }
    public static UsersRegisterResponseDto toResponse (Users userEntity){
        if (userEntity == null) return null;

        UsersRegisterResponseDto dto = new UsersRegisterResponseDto();

        dto.setId(userEntity.getId());
        dto.setUsername(userEntity.getUsername());
        dto.setEmail(userEntity.getEmail());
        dto.setDateOfBirth(userEntity.getDateOfBirth());
        dto.setPhone(userEntity.getPhone());
        dto.setRoleId(userEntity.getRole().getId());

        return dto;
    }
    public static Users toEntity (UsersLoginDto dto){
        if (dto == null) return null;

        Users user = new Users();

        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        return user;
    }


    public static Users updateToEntity(UsersUpdateDto dto){

        if (dto == null) return null;

        Users user = new Users();

        user.setId(dto.getId());
        user.setDeleted(dto.getDeleted());

        user.setModifiedAt(dto.getModifiedAt());
        user.setPassword(dto.getPassword());
        user.setUsername(dto.getUsername());

//        user.setRoleId(dto.getRoleId());

        return user;


    }
    public static UsersUpdateResponseDto updateToResponse (Users userEntity){
        if (userEntity == null) return null;

        UsersUpdateResponseDto dto = new UsersUpdateResponseDto();

        dto.setId(userEntity.getId());
        dto.setUsername(userEntity.getUsername());
        dto.setEmail(userEntity.getEmail());
        dto.setDateOfBirth(userEntity.getDateOfBirth());
        dto.setPhone(userEntity.getPhone());
        dto.setModifiedAt(userEntity.getModifiedAt());
        dto.setRoleId(userEntity.getRole().getId());

        return dto;
    }
}
