package com.ale182.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.ale182.cursomc.domain.Cliente;
import com.ale182.cursomc.dto.ClienteDTO;
import com.ale182.cursomc.repositories.ClienteRepository;
import com.ale182.cursomc.resources.exception.FieldMessage;

// Implementação do validator customizado

																// nome da anotação que vai validar , Tipo da Anotação
public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	// injetado no código para conseguir pegar a URI da chamada
	// o id do cliente está na URI e nao no Body da requisicao
	@Autowired
	private HttpServletRequest request;
	
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked") // retira erros do "casting"
		// acessa o map que é a estrutura de dados, chave/valor
																// pega o map de variaveis de URI que estao na requisicao
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
								  // casting para converter no tipo que precisa
		
		Integer uriId = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		// se o cliente é o mesmo, deixa passar na validacao, pois o cliente pode nao ter alterado o email
		// erro ocorre somente quando for de outro cliente
		if (aux != null && !aux.getId().equals(uriId) ) {
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