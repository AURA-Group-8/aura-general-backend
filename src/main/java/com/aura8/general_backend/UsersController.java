package com.aura8.general_backend;

import com.aura8.general_backend.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsersController {
    @Autowired
    private UsersRepository repository;

    @PostMapping
    public ResponseEntity<Users> cadastrar(@RequestBody Users users){

        Users usuarioRegistrado = this.repository.save(users);
        return ResponseEntity
                .status(201)
                .body(usuarioRegistrado);
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
