package com.ale182.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ale182.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	//nao vai criar transcao no DB , mais rapida e para evitar lock
	@Transactional(readOnly = true)
	Cliente findByEmail(String email);
	
}
