package com.airmont.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airmont.models.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
