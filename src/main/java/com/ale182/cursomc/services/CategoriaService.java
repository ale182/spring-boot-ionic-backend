package com.ale182.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ale182.cursomc.domain.Categoria;
import com.ale182.cursomc.repositories.CategoriaRepository;
import com.ale182.cursomc.services.exceptions.ObjectNotFoundException;
// import java.util.Optional;
		
@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo ;
	
	public Categoria buscar(Integer id) {
		Categoria obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id:" + id 
					+ " , Tipo: " + Categoria.class.getName()
					) ;
		}
			
		return obj;
	
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
}
