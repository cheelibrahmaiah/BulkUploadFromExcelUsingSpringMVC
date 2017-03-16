package com.enlume.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enlume.entities.Accounts;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, Integer> {

	Accounts findByUsernameAndPassword(String username, String password);

}
