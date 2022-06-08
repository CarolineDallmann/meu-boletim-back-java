package com.meuboletim.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.meuboletim.DTO.BoletimDTO;
import com.meuboletim.DTO.FrequenciaDTO;
import com.meuboletim.DTO.NotaDTO;
import com.meuboletim.entities.Frequencia;
import com.meuboletim.entities.Nota;
import com.meuboletim.repositories.FrequenciaRepository;
import com.meuboletim.repositories.NotaRepository;

@ExtendWith(SpringExtension.class)
public class BoletimServiceTest {

	private UUID alunoId;
	private short anoLetivo;
	private UUID materiaId1;
	private UUID materiaId2;
	private NotaDTO notaNovaDTO;
	private Nota novaNota;
	private FrequenciaDTO frequenciaNovaDTO;
	private Frequencia novaFrequencia;

	@InjectMocks
	private BoletimService service;

	@Mock
	private FrequenciaRepository frequenciaRepository;

	@Mock
	private NotaRepository notaRepository;

	@Captor
	private ArgumentCaptor<Nota> captorNota;

	@Captor
	private ArgumentCaptor<Frequencia> captorFrequencia;

	@BeforeEach
	void setup() {
		alunoId = UUID.randomUUID();
		anoLetivo = 2022;
		materiaId1 = UUID.randomUUID();
		materiaId2 = UUID.randomUUID();
		notaNovaDTO = new NotaDTO(materiaId1, alunoId, 2f, 3f, 4f, 5f, anoLetivo);
		novaNota = new Nota();
		novaNota.setId(UUID.randomUUID().toString());
		frequenciaNovaDTO = new FrequenciaDTO(alunoId, materiaId1, (short) 2, (short) 3, (short) 4, (short) 5,
				(short) 10, anoLetivo);
		novaFrequencia = new Frequencia();
		novaFrequencia.setId(UUID.randomUUID().toString());

	}

	@Test
	void verificaNotaSalvaQdoEncontraUmaNota() {
		Mockito.when(notaRepository.findFirstByAlunoIdAndMateriaIdAndAnoLetivo(alunoId, materiaId1, anoLetivo))
				.thenReturn(Optional.of(novaNota));

		service.salvarNota(notaNovaDTO);

		Mockito.verify(notaRepository, Mockito.times(1)).save(captorNota.capture());
		Nota notaSalva = captorNota.getAllValues().get(0);
		Assertions.assertEquals(novaNota.getId(), notaSalva.getId());
		Assertions.assertEquals(novaNota.getAlunoId(), notaSalva.getAlunoId());
		Assertions.assertEquals(novaNota.getMateriaId(), notaSalva.getMateriaId());
		Assertions.assertEquals(novaNota.getNotaBim1(), notaSalva.getNotaBim1());
		Assertions.assertEquals(novaNota.getNotaBim2(), notaSalva.getNotaBim2());
		Assertions.assertEquals(novaNota.getNotaBim3(), notaSalva.getNotaBim3());
		Assertions.assertEquals(novaNota.getNotaBim4(), notaSalva.getNotaBim4());
		Assertions.assertEquals(novaNota.getAnoLetivo(), notaSalva.getAnoLetivo());
	}

	@Test
	void verificaNotaSalvaQdoNaoEncontraUmaNota() {
		Mockito.when(notaRepository.findFirstByAlunoIdAndMateriaIdAndAnoLetivo(alunoId, materiaId1, anoLetivo))
				.thenReturn(Optional.empty());

		service.salvarNota(notaNovaDTO);

		Mockito.verify(notaRepository, Mockito.times(1)).save(captorNota.capture());
		Nota notaSalva = captorNota.getAllValues().get(0);
		Assertions.assertNotEquals(novaNota.getId(), notaSalva.getId());
	}

	@Test
	void verificaFrequenciaSalvaQdoEncontraUmaFrequencia() {
		Mockito.when(frequenciaRepository.findFirstByAlunoIdAndMateriaIdAndAnoLetivo(alunoId, materiaId1, anoLetivo))
				.thenReturn(Optional.of(novaFrequencia));

		service.salvarFrequencia(frequenciaNovaDTO);

		Mockito.verify(frequenciaRepository, Mockito.times(1)).save(captorFrequencia.capture());
		Frequencia frequenciaSalva = captorFrequencia.getAllValues().get(0);
		Assertions.assertEquals(novaFrequencia.getId(), frequenciaSalva.getId());
		Assertions.assertEquals(novaFrequencia.getAlunoId(), frequenciaSalva.getAlunoId());
		Assertions.assertEquals(novaFrequencia.getMateriaId(), frequenciaSalva.getMateriaId());
		Assertions.assertEquals(novaFrequencia.getQtdeFaltaBim1(), frequenciaSalva.getQtdeFaltaBim1());
		Assertions.assertEquals(novaFrequencia.getQtdeFaltaBim2(), frequenciaSalva.getQtdeFaltaBim2());
		Assertions.assertEquals(novaFrequencia.getQtdeFaltaBim3(), frequenciaSalva.getQtdeFaltaBim3());
		Assertions.assertEquals(novaFrequencia.getQtdeFaltaBim4(), frequenciaSalva.getQtdeFaltaBim4());
		Assertions.assertEquals(novaFrequencia.getQtdePresenca(), frequenciaSalva.getQtdePresenca());
		Assertions.assertEquals(novaFrequencia.getAnoLetivo(), frequenciaSalva.getAnoLetivo());
	}

	@Test
	void verificaFrequenciaSalvaQdoNaoEncontraUmaFrequencia() {
		Mockito.when(frequenciaRepository.findFirstByAlunoIdAndMateriaIdAndAnoLetivo(alunoId, materiaId1, anoLetivo))
				.thenReturn(Optional.empty());

		service.salvarFrequencia(frequenciaNovaDTO);

		Mockito.verify(frequenciaRepository, Mockito.times(1)).save(captorFrequencia.capture());
		Frequencia frequenciaSalva = captorFrequencia.getAllValues().get(0);
		Assertions.assertNotEquals(novaFrequencia.getId(), frequenciaSalva.getId());
	}

	@Test
	void verificaRetornoGetBoletim() {

		Nota nota1 = new Nota(UUID.randomUUID().toString(), materiaId1, alunoId, 1f, 2f, 3f, 4f, anoLetivo);
		Nota nota2 = new Nota(UUID.randomUUID().toString(), materiaId2, alunoId, 5f, 6f, 7f, 8f, anoLetivo);
		List<Nota> notas = new ArrayList<>();
		notas.add(nota1);
		notas.add(nota2);
		Mockito.when(notaRepository.findByAlunoIdAndAnoLetivo(alunoId, anoLetivo)).thenReturn(notas);

		Frequencia freq1 = new Frequencia(UUID.randomUUID().toString(), alunoId, materiaId1, (short) 2, (short) 3,
				(short) 4, (short) 5, anoLetivo, (short) 10);
		Frequencia freq2 = new Frequencia(UUID.randomUUID().toString(), alunoId, materiaId2, (short) 6, (short) 7,
				(short) 8, (short) 9, anoLetivo, (short) 11);
		List<Frequencia> frequencias = new ArrayList<>();
		frequencias.add(freq1);
		frequencias.add(freq2);
		Mockito.when(frequenciaRepository.findByAlunoIdAndAnoLetivo(alunoId, anoLetivo)).thenReturn(frequencias);

		List<BoletimDTO> boletim = service.getBoletim(alunoId, anoLetivo);
		BoletimDTO b1 = boletim.get(0);
		BoletimDTO b2 = boletim.get(1);
		Assertions.assertEquals(b1.getMateriaId(), materiaId1);
		Assertions.assertEquals(b1.getNotaBim1(), nota1.getNotaBim1());
		Assertions.assertEquals(b1.getNotaBim2(), nota1.getNotaBim2());
		Assertions.assertEquals(b1.getNotaBim3(), nota1.getNotaBim3());
		Assertions.assertEquals(b1.getNotaBim4(), nota1.getNotaBim4());
		Assertions.assertEquals(b1.getQtdeFaltaBim1(), freq1.getQtdeFaltaBim1());
		Assertions.assertEquals(b1.getQtdeFaltaBim2(), freq1.getQtdeFaltaBim2());
		Assertions.assertEquals(b1.getQtdeFaltaBim3(), freq1.getQtdeFaltaBim3());
		Assertions.assertEquals(b1.getQtdeFaltaBim4(), freq1.getQtdeFaltaBim4());
		Assertions.assertEquals(b1.getQtdePresenca(), freq1.getQtdePresenca());

		Assertions.assertEquals(b2.getMateriaId(), materiaId2);
		Assertions.assertEquals(b2.getNotaBim1(), nota2.getNotaBim1());
		Assertions.assertEquals(b2.getQtdeFaltaBim1(), freq2.getQtdeFaltaBim1());
	}

}
