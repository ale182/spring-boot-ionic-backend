package com.ale182.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.ale182.cursomc.domain.Cliente;
import com.ale182.cursomc.services.validation.ClienteUpdate;

// anotacao para validar o Cliente, na atualizacao
@ClienteUpdate
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatorio")
	@Length(min=5,max=120, message="Tamanho deve ser > 5 e < 120")
	private String nome;
	
	@NotEmpty(message="Preenchimento obrigatorio")
	@Email(message="Email invalido")
	private String email;
	
	public ClienteDTO () {
		
	}
	
	public ClienteDTO(Cliente obj) {
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
