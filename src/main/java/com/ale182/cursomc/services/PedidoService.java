package com.ale182.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ale182.cursomc.domain.Pedido;
import com.ale182.cursomc.repositories.PedidoRepository;
import com.ale182.cursomc.services.exceptions.ObjectNotFoundException;
// import java.util.Optional;
		
@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo ;
	
	public Pedido find(Integer id) {
		Pedido obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id:" + id 
					+ " , Tipo: " + Pedido.class.getName()
					) ;
		}
			
		return obj;
	
	}
	
}
