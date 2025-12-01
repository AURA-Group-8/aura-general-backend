package com.aura8.general_backend.clean_arch.infrastructure.persistence.repository.users;

import com.aura8.general_backend.clean_arch.core.domain.Users;
import com.aura8.general_backend.clean_arch.core.domain.valueobject.PageElement;
import com.aura8.general_backend.clean_arch.core.gateway.UsersGateway;
import com.aura8.general_backend.clean_arch.infrastructure.mapper.UsersMapper;
import com.aura8.general_backend.clean_arch.infrastructure.persistence.entity.UsersEntity;
import com.aura8.general_backend.clean_arch.application.exception.ElementNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        UsersEntity usersEntity = UsersMapper.toEntity(user);
        UsersEntity saved = repository.save(usersEntity);
        Users savedUser = UsersMapper.toDomain(saved);
        return savedUser;
    }

    @Override
    public List<Users> findAll() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
        List<UsersEntity> usersEntities = repository.findAllByDeletedFalse();
        List<Users> usersList = usersEntities.stream().map(UsersMapper::toDomain).toList();
        return usersList;
    }

    @Override
    public PageElement<Users> findAllPageable(Integer page, Integer size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<UsersEntity> usersPage = repository.findAllByDeletedFalse(pageable);
        List<Users> usersList = usersPage.getContent().stream().map(UsersMapper::toDomain).toList();
        PageElement<Users> pageElement = new PageElement<>(
                usersList,
                usersPage.getNumber(),
                usersPage.getSize(),
                usersPage.getTotalElements(),
                usersPage.getTotalPages()
        );
        return pageElement;
    }

    @Override
    public Optional<Users> findById(Integer id) {
        Optional<UsersEntity> usersEntity = repository.findByIdAndDeletedFalse(id);
        if (usersEntity.isEmpty()) return Optional.empty();
        Users user = UsersMapper.toDomain(usersEntity.get());
        return Optional.of(user);
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        Optional<UsersEntity> usersEntity = repository.findByEmailAndDeletedFalse(email);
        if (usersEntity.isEmpty()) return Optional.empty();
        Users user = UsersMapper.toDomain(usersEntity.get());
        return Optional.of(user);
    }

    @Override
    public Users patch(Users user, Integer userId) {
        Optional<UsersEntity> usersEntity = findById(userId).map(UsersMapper::toEntity);
        if(usersEntity.isEmpty()) throw new ElementNotFoundException("User de id: " + userId + " não encontrado");
        UsersEntity entity = usersEntity.get();
        UsersMapper.mergeToEntity(entity, user);
        UsersEntity saved = repository.save(entity);
        Users savedUser = UsersMapper.toDomain(saved);
        return savedUser;
    }

    @Override
    public void delete(Integer userId) {
        Optional<UsersEntity> usersEntity = repository.findByIdAndDeletedFalse(userId);
        if (usersEntity.isEmpty()) throw new ElementNotFoundException("User de id: " + userId + " não encontrado");
        UsersEntity userToDelete = usersEntity.get();
        userToDelete.setDeleted(true);
        repository.save(userToDelete);
    }

    @Override
    public Boolean existsByEmailOrPhone(String email, String phone) {
        return repository.existsByEmailAndDeletedFalseOrPhoneAndDeletedFalse(email, phone);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return repository.existsByEmailAndDeletedFalse(email);
    }
}
