package com.ale182.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.ale182.cursomc.services.validation.ClienteInsert;

// usa a anotacao customizada para validar CPF/CNPJ
@ClienteInsert
public class ClienteNewDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// DTO criado para pode inserir todos os dados do Cliente, Endereço e Telefone, na mesma transação
	
	// Dados do Cliente, para inserção
	@NotEmpty(message="Preenchimento obrigatorio")
	@Length(min=5,max=120, message="Tamanho deve ser > 5 e < 120")
	private String nome;
	
	@NotEmpty(message="Preenchimento obrigatorio")
	@Email(message="Email invalido")
	private String email;
	
	@NotEmpty(message="Preenchimento obrigatorio")
	/*  Validacao de CPF ou CNPJ 
		poderia usar as anotacoes padrao do pacotes ....br
		import org.hibernate.validator.constraints.br.CNPJ;
		import org.hibernate.validator.constraints.br.CPF;
		@CPF
		@CNPJ
	*/	
	private String cpfOuCnpj ;
	private Integer tipo ;
	
	// Dados do Endereço
	@NotEmpty(message="Preenchimento obrigatorio")
	private String logradouro ;
	@NotEmpty(message="Preenchimento obrigatorio")
	private String numero ;
	private String complemento ;
	private String bairro ;
	@NotEmpty(message="Preenchimento obrigatorio")
	private String cep ;
	
	// Dados de telefone
	@NotEmpty(message="Preenchimento obrigatorio")
	private String telefone1;
	private String telefone2;
	private String telefone3;
	
	// Id da Cidade
	private Integer cidadeId;
	
	public ClienteNewDTO () {
		
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

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getTelefone3() {
		return telefone3;
	}

	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}

	public Integer getCidadeId() {
		return cidadeId;
	}

	public void setCidadeId(Integer cidadeId) {
		this.cidadeId = cidadeId;
	}
	
	
}
