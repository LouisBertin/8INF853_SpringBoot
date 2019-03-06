package com.example.demo.repository;

import com.example.demo.entity.Figurine;
import org.springframework.data.repository.CrudRepository;

public interface FigurineRepository extends CrudRepository<Figurine, Integer> {
}
