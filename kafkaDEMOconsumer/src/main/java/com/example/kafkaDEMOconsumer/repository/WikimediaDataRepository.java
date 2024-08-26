package com.example.kafkaDEMOconsumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.kafkaDEMOconsumer.entity.WikimediaData;

public interface WikimediaDataRepository extends JpaRepository<WikimediaData, Long>{
    
}
