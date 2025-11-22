package com.aura8.general_backend.clean_arch.infrastructure.mapper;

import com.aura8.general_backend.clean_arch.application.usecase.users.create.CreateUsersCommand;
import com.aura8.general_backend.clean_arch.application.usecase.users.login.LoginUsersCommand;
import com.aura8.general_backend.clean_arch.application.usecase.users.patch.PatchUsersCommand;
import com.aura8.general_backend.clean_arch.core.domain.Role;
import com.aura8.general_backend.clean_arch.core.domain.Users;
import com.aura8.general_backend.clean_arch.core.domain.attribute.Email;
import com.aura8.general_backend.clean_arch.core.domain.attribute.Password;
import com.aura8.general_backend.clean_arch.core.domain.attribute.Phone;
import com.aura8.general_backend.clean_arch.core.domain.attribute.Username;
import com.aura8.general_backend.clean_arch.infrastructure.dto.users.*;
import com.aura8.general_backend.clean_arch.infrastructure.persistence.entity.UsersEntity;

public class UsersMapper {
    public static Users toDomain(UsersEntity entity) {
        if (entity == null) {
            return null;
        }

        Username username = new Username(entity.getUsername());
        Email email = new Email(entity.getEmail());
        Phone phone = new Phone(entity.getPhone());
        Password password = new Password(entity.getPassword());

        return new Users(
                entity.getId(),
                username,
                email,
                password,
                phone,
                entity.getDateOfBirth(),
                entity.getObservation(),
                RoleMapper.toDomain(entity.getRole()),
                entity.getDeleted()
        );
    }

    public static Users toDomain(CreateUsersCommand command, String encodedPassword, Role role) {
        if (command == null) {
            return null;
        }

        Username username = new Username(command.username());
        Email email = new Email(command.email());
        Phone phone = new Phone(command.phone());
        Password password = new Password(encodedPassword);

        return new Users(
                username,
                email,
                password,
                phone,
                command.dateOfBirth(),
                role
        );
    }

    public static UsersEntity toEntity(Users domain) {
        if (domain == null) {
            return null;
        }
        return new UsersEntity(
                domain.getId(),
                domain.getUsername().get(),
                domain.getEmail().get(),
                domain.getPassword().get(),
                domain.getPhone().get(),
                domain.getDateOfBirth(),
                RoleMapper.toEntity(domain.getRole())
        );
    }

    public static CreateUsersCommand toCommand(CreateUsersRequest entity) {
        if (entity == null) {
            return null;
        }
        return new CreateUsersCommand(
                entity.username(),
                entity.email(),
                entity.password(),
                entity.phone(),
                entity.dateOfBirth()
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
                users.getRole(),
                users.getObservation()
        );
    }

    public static CreateUsersResponse toCreateResponse(Users users) {
        if (users == null) {
            return null;
        }

        return new CreateUsersResponse(
                users.getId(),
                users.getUsername().get(),
                users.getEmail().get(),
                users.getPhone().get(),
                users.getDateOfBirth(),
                users.getRole(),
                users.getObservation()
        );
    }

    public static PatchUsersResponse toPatchResponse(Users users) {
        if (users == null) {
            return null;
        }

        return new PatchUsersResponse(
                users.getId(),
                users.getUsername().get(),
                users.getEmail().get(),
                users.getPhone().get(),
                users.getDateOfBirth(),
                users.getObservation(),
                users.getRole()
        );
    }

    public static PatchUsersCommand toCommand(PatchUsersRequest request) {
        if (request == null) {
            return null;
        }
        return new PatchUsersCommand(
                request.id(),
                request.username(),
                request.email(),
                request.password(),
                request.phone(),
                request.dateOfBirth(),
                request.observation(),
                request.roleId(),
                null
        );
    }

    public static void mergeToDomain(Users users, PatchUsersCommand command) {
        if (command.username() != null) {
            users.setUsername(new Username(command.username()));
        }
        if (command.email() != null) {
            users.setEmail(new Email(command.email()));
        }
        if (command.password() != null) {
            users.setPassword(new Password(command.password()));
        }
        if (command.phone() != null) {
            users.setPhone(new Phone(command.phone()));
        }
        if (command.dateOfBirth() != null) {
            users.setDateOfBirth(command.dateOfBirth());
        }
        if (command.observation() != null) {
            users.setObservation(command.observation());
        }
        if (command.role() != null) {
            users.setRole(command.role());
        }
    }

    public static void mergeToEntity(UsersEntity entity, Users domain) {
        if (domain.getUsername() != null) {
            entity.setUsername(domain.getUsername().get());
        }
        if (domain.getEmail() != null) {
            entity.setEmail(domain.getEmail().get());
        }
        if (domain.getPassword() != null) {
            entity.setPassword(domain.getPassword().get());
        }
        if (domain.getPhone() != null) {
            entity.setPhone(domain.getPhone().get());
        }
        if (domain.getDateOfBirth() != null) {
            entity.setDateOfBirth(domain.getDateOfBirth());
        }
        if (domain.getRole() != null) {
            entity.setRole(RoleMapper.toEntity(domain.getRole()));
        }
        if (domain.getObservation() != null) {
            entity.setObservation(domain.getObservation());
        }
    }

    public static LoginUsersCommand toLoginCommand(LoginUsersRequest request) {
        if (request == null) {
            return null;
        }
        return new LoginUsersCommand(
                request.getEmail(),
                request.getPassword()
        );
    }
}
