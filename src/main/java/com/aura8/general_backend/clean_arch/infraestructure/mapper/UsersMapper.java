package com.aura8.general_backend.clean_arch.infraestructure.mapper;

import com.aura8.general_backend.clean_arch.application.usecase.users.create.CreateUsersCommand;
import com.aura8.general_backend.clean_arch.application.usecase.users.patch.PatchUsersCommand;
import com.aura8.general_backend.clean_arch.core.domain.Users;
import com.aura8.general_backend.clean_arch.core.domain.attribute.Email;
import com.aura8.general_backend.clean_arch.core.domain.attribute.Password;
import com.aura8.general_backend.clean_arch.core.domain.attribute.Phone;
import com.aura8.general_backend.clean_arch.core.domain.attribute.Username;
import com.aura8.general_backend.clean_arch.infraestructure.dto.users.*;
import com.aura8.general_backend.clean_arch.infraestructure.persistence.entity.UsersEntity;

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
                entity.getRoleId(),
                entity.getDeleted()
        );
    }

    public static Users toDomain(CreateUsersCommand command) {
        if (command == null) {
            return null;
        }

        Username username = new Username(command.username());
        Email email = new Email(command.email());
        Phone phone = new Phone(command.phone());
        Password password = new Password(command.password());

        return new Users(
                username,
                email,
                password,
                phone,
                command.dateOfBirth(),
                command.roleId()
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
                domain.getRoleId()
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
                entity.dateOfBirth(),
                entity.roleId()
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
                users.getRoleId(),
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
                users.getRoleId(),
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
                users.getRoleId()
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
                request.roleId()
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
        if (command.roleId() != null) {
            users.setRoleId(command.roleId());
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
        if (domain.getRoleId() != null) {
            entity.setRoleId(domain.getRoleId());
        }
        if (domain.getObservation() != null) {
            entity.setObservation(domain.getObservation());
        }
    }
}
