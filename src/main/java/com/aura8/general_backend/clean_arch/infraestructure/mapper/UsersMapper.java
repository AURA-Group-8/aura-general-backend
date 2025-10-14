package com.aura8.general_backend.clean_arch.infraestructure.mapper;

import com.aura8.general_backend.clean_arch.core.domain.Role;
import com.aura8.general_backend.clean_arch.core.domain.Users;
import com.aura8.general_backend.clean_arch.core.domain.attribute.Email;
import com.aura8.general_backend.clean_arch.core.domain.attribute.Password;
import com.aura8.general_backend.clean_arch.core.domain.attribute.Phone;
import com.aura8.general_backend.clean_arch.core.domain.attribute.Username;
import com.aura8.general_backend.clean_arch.infraestructure.dto.users.FindUsersResponse;
import com.aura8.general_backend.clean_arch.infraestructure.persistence.entity.UsersEntity;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;

public class UsersMapper {
    public static Users toDomain(UsersEntity entity) {
        if (entity == null) {
            return null;
        }

        Username username = new Username(entity.getUsername());
        Email email = new Email(entity.getEmail());
        Phone phone = new Phone(entity.getPhone());
        Password password = new Password(entity.getPassword());
        Role role = RoleMapper.toDomain(entity.getRole());

        return new Users(
                entity.getId(),
                username,
                email,
                password,
                phone,
                entity.getDateOfBirth(),
                entity.getObservation(),
                role,
                entity.getDeleted()
        );
    }

    public static FindUsersResponse toResponse(Users users) {
        if (users == null) {
            return null;
        }

        return new FindUsersResponse(
                users.getId(),
                users.getUsername().get(),
                users.getEmail().get(),
                users.getPhone().get(),
                users.getDateOfBirth(),
                users.getRole() != null ? users.getRole().getId() : null,
                users.getObservation()
        );
    }
}
