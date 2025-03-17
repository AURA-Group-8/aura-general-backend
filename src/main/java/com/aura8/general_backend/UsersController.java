package com.aura8.general_backend;

import com.aura8.general_backend.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsersController {
    @Autowired
    private UsersRepository repository;

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<Users> cadastrar(@RequestBody Users users){

        Optional<Users> usersOptional = repository.findByEmail(users.getEmail());
        if (usersOptional.isPresent()){
            return ResponseEntity.status(409).build();
        }
        Users usuarioRegistrado = this.repository.save(users);
        return ResponseEntity
                .status(201)
                .body(usuarioRegistrado);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public ResponseEntity<Users> logar(@RequestBody Users userInformations){
        Optional<Users> usersOptional = repository.findByEmailAndPassword(userInformations.getEmail(), userInformations.getPassword());
        return ResponseEntity.of(usersOptional);
    }

    @GetMapping
    public ResponseEntity<List<Users>> BuscarUsuario(){
        List<Users> todos = repository.findAllByDeletedFalse();
        if (todos.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(todos);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> BuscarUsuario(@PathVariable Integer id){

        return ResponseEntity.of(repository.findById(id));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> atualizar (@PathVariable Integer id, @RequestBody Users usuarioParaAtualizar){
        boolean existe = repository.existsById(id);

        if (existe){
            usuarioParaAtualizar.setId(id);
            Users usuarioNovo = repository.save(usuarioParaAtualizar);
            return ResponseEntity.status(200).body(usuarioNovo);
        }
        return ResponseEntity.status(404).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deletar(@PathVariable Integer id){
        if(repository.existsById(id)){
            Users usuarioModificar = repository.getById(id);
            usuarioModificar.setDeleted(true);
            repository.save(usuarioModificar);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }
}
