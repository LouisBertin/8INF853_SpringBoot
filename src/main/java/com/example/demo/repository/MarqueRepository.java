package com.example.demo.repository;

import com.example.demo.entity.Marque;
import org.springframework.data.repository.CrudRepository;

public interface MarqueRepository extends CrudRepository<Marque, Integer> {
}
