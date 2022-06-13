package com.meuboletim.services;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.meuboletim.DTO.FrequenciaDTO;
import com.meuboletim.DTO.NotaDTO;
import com.meuboletim.repositories.FrequenciaRepository;
import com.meuboletim.repositories.NotaRepository;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class BoletimServiceRepositoryIntegracaoTest {

	private static UUID alunoId = UUID.randomUUID();
	private static short anoLetivo = (short) 2022;
	private static UUID materiaId = UUID.randomUUID();
	private NotaDTO notaNovaDTO = new NotaDTO();
	private FrequenciaDTO frequenciaNovaDTO = new FrequenciaDTO();

	@Autowired
	private BoletimService service;

	@Autowired
	private FrequenciaRepository frequenciaRepository;

	@Autowired
	private NotaRepository notaRepository;

	@Test
	@Order(1)
	void insereNota() {

		notaNovaDTO.setAlunoId(alunoId);
		notaNovaDTO.setMateriaId(materiaId);
		notaNovaDTO.setAnoLetivo(anoLetivo);
		notaNovaDTO.setNotaBim1(5);
		notaNovaDTO.setNotaBim2(5);
		notaNovaDTO.setNotaBim3(5);
		notaNovaDTO.setNotaBim4(5);

		Assertions.assertEquals("Ok", service.salvarNota(notaNovaDTO));
	}

	@Test
	@Order(2)
	void insereFrequencia() {
		frequenciaNovaDTO.setAlunoId(alunoId);
		frequenciaNovaDTO.setMateriaId(materiaId);
		frequenciaNovaDTO.setAnoLetivo(anoLetivo);
		frequenciaNovaDTO.setQtdeFaltaBim1((short) 1);
		frequenciaNovaDTO.setQtdeFaltaBim2((short) 1);
		frequenciaNovaDTO.setQtdeFaltaBim3((short) 1);
		frequenciaNovaDTO.setQtdeFaltaBim4((short) 1);
		frequenciaNovaDTO.setQtdePresenca((short) 1);

		Assertions.assertEquals("Ok", service.salvarFrequencia(frequenciaNovaDTO));
	}

//	@Test
//	@Order(3)
//	void verificaRetornoGetBoletim() {
//
//		List<BoletimDTO> boletim = service.getBoletim(alunoId, anoLetivo);
//		BoletimDTO b1 = boletim.get(0);
//
//		Assertions.assertEquals(b1.getMateriaId(), materiaId);
//		Assertions.assertEquals(b1.getNotaBim1(), 5);
//
//		Assertions.assertEquals(b1.getQtdeFaltaBim1(), 1);
//	}

}
