package com.aura8.general_backend.service;

import com.aura8.general_backend.entities.Scheduling;
import com.aura8.general_backend.entities.Users;
import com.aura8.general_backend.event.SchedulingCreateEvent;
import com.aura8.general_backend.exception.ElementNotFoundException;
import com.aura8.general_backend.repository.SchedulingRepository;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SchedulingService {

    private final ApplicationEventPublisher publisher;
    private final SchedulingRepository schedulingRepository;
    private final UsersService usersService;

    public SchedulingService(SchedulingRepository schedulingRepository, UsersService usersService, ApplicationEventPublisher publisher) {
        this.schedulingRepository = schedulingRepository;
        this.usersService = usersService;
        this.publisher = publisher;
    }

    public Scheduling findById(Integer id){
        return schedulingRepository.findById(id).orElseThrow(
                () -> (new ElementNotFoundException("Scheduling de ID: %d não foi encontrado".formatted(id)))
        );
    }

    public Scheduling create(Scheduling scheduling, Integer userId){
        Users user = usersService.getUserById(userId);
        scheduling.setUsers(user);
        Boolean isAdminScheduling = user.getRole().getName().equals("ADMIN");
        publisher.publishEvent(new SchedulingCreateEvent(this, isAdminScheduling, user, scheduling));
        return schedulingRepository.save(scheduling);
    }

    public Page<Scheduling> findAll(Pageable pageable){
        return schedulingRepository.findAll(pageable);
    }
}
