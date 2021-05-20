package com.example.appjparelationships.repository;

import com.example.appjparelationships.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
