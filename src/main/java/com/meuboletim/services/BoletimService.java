package com.meuboletim.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meuboletim.DTO.BoletimDTO;
import com.meuboletim.DTO.FrequenciaDTO;
import com.meuboletim.DTO.NotaDTO;
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
			b.setQtdePresenca(f.getQtdePresenca());

			boletim.add(b);
		}
		return boletim;
	}

	public String salvarNota(NotaDTO nota) {
		Nota notaEncontrada = notaRepository
				.findFirstByAlunoIdAndMateriaIdAndAnoLetivo(nota.getAlunoId(), nota.getMateriaId(), nota.getAnoLetivo())
				.orElse(new Nota(UUID.randomUUID().toString()));

		notaEncontrada.setAlunoId(nota.getAlunoId());
		notaEncontrada.setAnoLetivo(nota.getAnoLetivo());
		notaEncontrada.setMateriaId(nota.getMateriaId());
		notaEncontrada.setNotaBim1(nota.getNotaBim1());
		notaEncontrada.setNotaBim2(nota.getNotaBim2());
		notaEncontrada.setNotaBim3(nota.getNotaBim3());
		notaEncontrada.setNotaBim4(nota.getNotaBim4());

		notaRepository.save(notaEncontrada);

		return "Ok";
	}

	public String salvarFrequencia(FrequenciaDTO frequencia) {
		Frequencia freqEncontrada = frequenciaRepository
				.findFirstByAlunoIdAndMateriaIdAndAnoLetivo(frequencia.getAlunoId(), frequencia.getMateriaId(),
						frequencia.getAnoLetivo())
				.orElse(new Frequencia(UUID.randomUUID().toString()));

		freqEncontrada.setAlunoId(frequencia.getAlunoId());
		freqEncontrada.setAnoLetivo(frequencia.getAnoLetivo());
		freqEncontrada.setMateriaId(frequencia.getMateriaId());
		freqEncontrada.setQtdeFaltaBim1(frequencia.getQtdeFaltaBim1());
		freqEncontrada.setQtdeFaltaBim2(frequencia.getQtdeFaltaBim2());
		freqEncontrada.setQtdeFaltaBim3(frequencia.getQtdeFaltaBim3());
		freqEncontrada.setQtdeFaltaBim4(frequencia.getQtdeFaltaBim4());
		freqEncontrada.setQtdePresenca(frequencia.getQtdePresenca());

		frequenciaRepository.save(freqEncontrada);

		return "Ok";
	}
}
