package com.ale182.cursomc.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ale182.cursomc.domain.Cidade;
import com.ale182.cursomc.domain.Cliente;
import com.ale182.cursomc.domain.Endereco;
import com.ale182.cursomc.domain.enums.TipoCliente;
import com.ale182.cursomc.dto.ClienteDTO;
import com.ale182.cursomc.dto.ClienteNewDTO;
import com.ale182.cursomc.repositories.CidadeRepository;
import com.ale182.cursomc.repositories.ClienteRepository;
import com.ale182.cursomc.repositories.EnderecoRepository;
import com.ale182.cursomc.services.exceptions.DataIntegrityException;
import com.ale182.cursomc.services.exceptions.ObjectNotFoundException;
// import java.util.Optional;
		
@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo ;
	
	// UPDATE do curso, para nao usar o cidadeRepository, mas direto a cidade
	//@Autowired
	//private CidadeRepository cidadeRepository ;
	
	@Autowired
	private EnderecoRepository enderecoRepository ;	
	
	public Cliente find(Integer id) {
		Cliente obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id:" + id 
					+ " , Tipo: " + Cliente.class.getName()
					) ;
		}
			
		return obj;
	
	}

	// anotacao para salvar na mesma transacao do DB
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		// salva os endereços no banco, antes de retornar o insert do método
		enderecoRepository.save(obj.getEnderecos());
		return  obj ;
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
	
	// sobrecarga do método , usando a classe ClienteNewDTO
	public Cliente fromDTO(ClienteNewDTO objDto) {
		
		// ID é nulo, pq está na inserção, auto_increment
		Cliente cli = new Cliente(null , objDto.getNome() , objDto.getEmail(),objDto.getCpfOuCnpj() , TipoCliente.toEnum(objDto.getTipo()));
		// UPDATE do curso, para nao usar o cidadeRepository, mas direto a cidade
		//Cidade cid = cidadeRepository.findOne(objDto.getCidadeId());
		Cidade cid = new Cidade(objDto.getCidadeId(),null,null);
		Endereco end = new Endereco(null,objDto.getLogradouro() , objDto.getNumero() , objDto.getComplemento() , objDto.getBairro(),objDto.getCep(),cli,cid);
		
		// incluir endereço na lista de endereços do Cliente
		cli.getEnderecos().add(end);
		
		// incluir telefones no Cliente
		cli.getTelefones().add(objDto.getTelefone1());
		
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}

		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		
		return cli ;
	}
	
	
	private void updateData(Cliente newObj , Cliente obj) {
		// busca os dados do Objeto atual para atualizar no novo
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
}
