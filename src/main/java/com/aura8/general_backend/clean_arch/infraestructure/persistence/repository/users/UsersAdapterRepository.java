package com.aura8.general_backend.clean_arch.infraestructure.persistence.repository.users;

import com.aura8.general_backend.clean_arch.core.domain.Users;
import com.aura8.general_backend.clean_arch.core.gateway.UsersGateway;
import com.aura8.general_backend.clean_arch.infraestructure.mapper.UsersMapper;
import com.aura8.general_backend.clean_arch.infraestructure.persistence.entity.UsersEntity;
import com.aura8.general_backend.clean_arch.application.exception.ElementNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsersAdapterRepository implements UsersGateway {

    private final UsersJpaRepository repository;

    public UsersAdapterRepository(UsersJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Users save(Users user) {
        return null;
    }

    @Override
    public List<Users> findAll() {
        List<UsersEntity> usersEntities = repository.findAllByDeletedFalse();
        List<Users> usersList = usersEntities.stream().map(UsersMapper::toDomain).toList();
        return usersList;
    }

    @Override
    public Optional<Users> findById(Integer id) {
        Optional<UsersEntity> usersEntity = repository.findByIdAndDeletedFalse(id);
        if (usersEntity.isEmpty()) return Optional.empty();
        Users user = UsersMapper.toDomain(usersEntity.get());
        return Optional.of(user);
    }

    @Override
    public Users findByEmail(String email) {
        return null;
    }

    @Override
    public Users patch(Users user) {
        return null;
    }

    @Override
    public void delete(Integer userId) {
        Optional<UsersEntity> usersEntity = repository.findByIdAndDeletedFalse(userId);
        if (usersEntity.isEmpty()) throw new ElementNotFoundException("User de id: " + userId + " não encontrado");
        UsersEntity userToDelete = usersEntity.get();
        userToDelete.setDeleted(true);
        repository.save(userToDelete);
    }
}
