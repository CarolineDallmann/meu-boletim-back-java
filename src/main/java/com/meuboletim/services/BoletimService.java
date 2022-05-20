package com.meuboletim.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meuboletim.DTO.BoletimDTO;
import com.meuboletim.entities.Frequencia;
import com.meuboletim.entities.Nota;
import com.meuboletim.repositories.FrequenciaRepository;
import com.meuboletim.repositories.NotaRepository;

@Service
public class BoletimService {

	@Autowired
	NotaRepository notaRepository;

	@Autowired
	FrequenciaRepository frequenciaRepository;

//	public List<Pessoa> list(TipoPessoa tipo, String nome, boolean mostrarInativos) {
//		return pessoaRepository.list(tipo, nome, mostrarInativos);
//	}

	public List<BoletimDTO> getBoletim(UUID alunoId, short anoLetivo) {
		List<Nota> notas = notaRepository.findByAlunoIdAndAnoLetivo(alunoId, anoLetivo);
		List<Frequencia> frequencias = frequenciaRepository.findByAlunoIdAndAnoLetivo(alunoId, anoLetivo);
		List<BoletimDTO> boletim = new ArrayList<>();
		for (Nota nota : notas) {
			BoletimDTO b = new BoletimDTO();
			Frequencia f = frequencias.stream().filter(freq -> freq.getMateriaId().equals(nota.getMateriaId()))
					.findFirst().orElse(new Frequencia());
			b.setMateriaId(nota.getMateriaId());
			b.setNotaBim1(nota.getNotaBim1());
			b.setNotaBim2(nota.getNotaBim2());
			b.setNotaBim3(nota.getNotaBim3());
			b.setNotaBim4(nota.getNotaBim4());
			b.setQtdeFaltaBim1(f.getQtdeFaltaBim1());
			b.setQtdeFaltaBim2(f.getQtdeFaltaBim2());
			b.setQtdeFaltaBim3(f.getQtdeFaltaBim3());
			b.setQtdeFaltaBim4(f.getQtdeFaltaBim4());

			boletim.add(b);
		}
		return boletim;
	}
}
