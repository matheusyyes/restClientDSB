package com.devsuperior.atividade.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.atividade.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

}
