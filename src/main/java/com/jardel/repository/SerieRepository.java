package com.jardel.repository;

import com.jardel.models.Serie;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SerieRepository extends CrudRepository<Serie, Integer>{

}