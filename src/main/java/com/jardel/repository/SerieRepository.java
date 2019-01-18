package com.jardel.repository;

import java.util.List;

import com.jardel.models.Serie;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SerieRepository extends CrudRepository<Serie, Integer>{

    @Query("select s from Serie s where s.usuario.id = ?1")
    List<Serie> findSerieByUser(int usuario_id);

}