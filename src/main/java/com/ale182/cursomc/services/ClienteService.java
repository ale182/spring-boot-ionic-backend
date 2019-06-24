package com.ale182.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ale182.cursomc.domain.Cliente;
import com.ale182.cursomc.repositories.ClienteRepository;
import com.ale182.cursomc.services.exceptions.ObjectNotFoundException;
// import java.util.Optional;
		
@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo ;
	
	public Cliente buscar(Integer id) {
		Cliente obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id:" + id 
					+ " , Tipo: " + Cliente.class.getName()
					) ;
		}
			
		return obj;
	
	}
	
}
