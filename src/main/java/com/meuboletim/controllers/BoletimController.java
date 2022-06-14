package com.meuboletim.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meuboletim.dto.BoletimDTO;
import com.meuboletim.dto.FrequenciaDTO;
import com.meuboletim.dto.NotaDTO;
import com.meuboletim.exceptions.ExceptionDefault;
import com.meuboletim.services.BoletimService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/boletim")

public class BoletimController {

	@Autowired
	BoletimService boletimService;

	@GetMapping
	@CrossOrigin(origins = "#{systemEnvironment['FRONT_URL']}")
	@ApiOperation(value = "Retorna boletim de um aluno por id e por ano letivo")
	public List<BoletimDTO> getBoletim(@RequestParam(required = false, defaultValue = "") String alunoId,
			@RequestParam(required = false, defaultValue = "1") short anoLetivo) throws Exception {

		if (alunoId.isEmpty()) {
			throw new ExceptionDefault("Informe o aluno");
		}
		if (anoLetivo == 1) {
			throw new ExceptionDefault("Informe o ano");
		}

		return boletimService.getBoletim(UUID.fromString(alunoId), anoLetivo);
	}

	@PostMapping("/nota")
	@CrossOrigin(origins = "#{systemEnvironment['NODE_URL']}")
	@ApiOperation(value = "Salva uma nota por aluno, por matéria e por ano letivo")
	public String save(@Valid @RequestBody NotaDTO nota, Errors errors) {
		if (errors.hasErrors()) {
			throw new ExceptionDefault(
					// Optional.of(errors.getFieldError()).map(FieldError::getDefaultMessage).orElse(""));
					errors.getFieldError().getDefaultMessage());
		}

		return boletimService.salvarNota(nota);
	}

	@PostMapping("/frequencia")
	@CrossOrigin(origins = "#{systemEnvironment['NODE_URL']}")
	@ApiOperation(value = "Salva uma frequência por aluno, por matéria e por ano letivo")
	public String save(@Valid @RequestBody FrequenciaDTO frequencia, Errors errors) {
		if (errors.hasErrors()) {
			throw new ExceptionDefault(
					Optional.of(errors.getFieldError()).map(FieldError::getDefaultMessage).orElse(""));
		}
		return boletimService.salvarFrequencia(frequencia);
	}

}