package com.meuboletim.controllers;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meuboletim.DTO.BoletimDTO;
import com.meuboletim.DTO.FrequenciaDTO;
import com.meuboletim.DTO.NotaDTO;
import com.meuboletim.exceptions.ExceptionDefault;
import com.meuboletim.services.BoletimService;

@RestController
@RequestMapping("/boletim")

public class BoletimController {

	@Autowired
	BoletimService boletimService;

	@CrossOrigin(origins = "#{systemEnvironment['FRONT_URL']}")
	@GetMapping
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

	@CrossOrigin(origins = "#{systemEnvironment['NODE_URL']}")
	@PostMapping("/nota")
	public String save(@Valid @RequestBody NotaDTO nota, Errors errors) {
		if (errors.hasErrors()) {
			throw new ExceptionDefault(errors.getFieldError().getDefaultMessage());
		}

		return boletimService.salvarNota(nota);
	}

	@CrossOrigin(origins = "#{systemEnvironment['NODE_URL']}")
	@PostMapping("/frequencia")
	public String save(@Valid @RequestBody FrequenciaDTO frequencia, Errors errors) {
		if (errors.hasErrors()) {
			throw new ExceptionDefault(errors.getFieldError().getDefaultMessage());
		}
		return boletimService.salvarFrequencia(frequencia);
	}

}