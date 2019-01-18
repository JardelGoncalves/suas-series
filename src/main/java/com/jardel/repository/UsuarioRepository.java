package com.jardel.repository;

import com.jardel.models.Usuario;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

    @Query("select u from Usuario u where u.email = ?1")
    Usuario findUserByEmail(String email);

}