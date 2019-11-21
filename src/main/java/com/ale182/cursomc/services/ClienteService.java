package com.ale182.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ale182.cursomc.domain.Cliente;
import com.ale182.cursomc.domain.Cliente;
import com.ale182.cursomc.dto.ClienteDTO;
import com.ale182.cursomc.repositories.ClienteRepository;
import com.ale182.cursomc.services.exceptions.DataIntegrityException;
import com.ale182.cursomc.services.exceptions.ObjectNotFoundException;
// import java.util.Optional;
		
@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo ;
	
	public Cliente find(Integer id) {
		Cliente obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id:" + id 
					+ " , Tipo: " + Cliente.class.getName()
					) ;
		}
			
		return obj;
	
	}

	public Cliente update(Cliente obj) {
	 	// Alterado para alterar o CPF, instanciando um Cliente a partir do Banco de DAdos
		// ANTERIOR = find(obj.getId());
		Cliente newObj = find(obj.getId());
		// atualiza os dados do novo objeto, de acordo com o objeto que veio como argumento
		updateData(newObj,obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.delete(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir devido relacionamentos");
		}
		
	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
		
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId() , objDto.getNome() , objDto.getEmail(),null , null);
	}
	
	private void updateData(Cliente newObj , Cliente obj) {
		// busca os dados do Objeto atual para atualizar no novo
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
}
