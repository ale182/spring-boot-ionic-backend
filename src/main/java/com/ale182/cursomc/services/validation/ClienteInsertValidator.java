package com.ale182.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.ale182.cursomc.domain.Cliente;
import com.ale182.cursomc.domain.enums.TipoCliente;
import com.ale182.cursomc.dto.ClienteNewDTO;
import com.ale182.cursomc.repositories.ClienteRepository;
import com.ale182.cursomc.resources.exception.FieldMessage;
import com.ale182.cursomc.services.validation.utils.BR;

// Implementação do validator customizado

																// nome da anotação que vai validar , Tipo da Anotação
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	@Override
	public void initialize(ClienteInsert ann) {
	}
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		// aqui sao as validacoes personalizadas, adicionando em uma lista de erros
		
		// valida se é pessoa fisica (usando o enumerado) e se nao eh valido (usando classe de validacao)
		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && ! BR.isValidCPF(objDto.getCpfOuCnpj()) ) {
			// adiciona erro na lista
			list.add(new FieldMessage("cpfOuCnpj","CPF Invalido"));
		}
		
		// valida se é pessoa juridica (usando o enumerado) e se nao eh valido (usando classe de validacao)
		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && ! BR.isValidCNPJ(objDto.getCpfOuCnpj()) ) {
			// adiciona erro na lista
			list.add(new FieldMessage("cpfOuCnpj","CNPJ Invalido"));
		}
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		// se objeto <> de nulo indica q ele encontrou objeto através do email, ou seja, ele já é cadastrado
		if (aux != null) {
			list.add(new FieldMessage("email","E-mail já existe"));
		}
		
		// esse for adiciona os erros personalizados aos erros do framework, para usar os recursos dele
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}