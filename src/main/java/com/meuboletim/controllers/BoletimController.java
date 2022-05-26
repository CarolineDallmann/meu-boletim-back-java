package com.meuboletim.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meuboletim.DTO.BoletimDTO;
import com.meuboletim.DTO.FrequenciaDTO;
import com.meuboletim.DTO.NotaDTO;
import com.meuboletim.services.BoletimService;

@RestController
@RequestMapping("/boletim")
public class BoletimController {

	@Autowired
	BoletimService boletimService;

	@GetMapping
	public List<BoletimDTO> getBoletim(@RequestParam UUID alunoId, @RequestParam short anoLetivo) {
		return boletimService.getBoletim(alunoId, anoLetivo);
	}

	@PostMapping("/nota")
	public String save(@RequestBody NotaDTO nota) {
		return boletimService.salvarNota(nota);
	}

	@PostMapping("/frequencia")
	public String save(@RequestBody FrequenciaDTO frequencia) {
		return boletimService.salvarFrequencia(frequencia);
	}

}