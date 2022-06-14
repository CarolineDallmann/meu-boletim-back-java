package com.meuboletim.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meuboletim.dto.FrequenciaDTO;
import com.meuboletim.dto.NotaDTO;
import com.meuboletim.services.BoletimService;

@SpringBootTest
@AutoConfigureMockMvc
class BoletimControllerServiceIntegracaoTest {

	private static UUID alunoId = UUID.randomUUID();
	private static short anoLetivo = (short) 2022;
	private static UUID materiaId = UUID.randomUUID();
	private NotaDTO notaNovaDTO = new NotaDTO();
	private FrequenciaDTO frequenciaNovaDTO = new FrequenciaDTO();

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BoletimService service;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void verificaRetornoGetBoletim() throws Exception {
		ResultActions result = mockMvc
				.perform(get("/boletim?alunoId={alunoId}&anoLetivo={anoLetivo}", alunoId, anoLetivo)
						.accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());

	}

	@Test
	void verificaRetornoPostNota() throws Exception {

		notaNovaDTO.setAlunoId(alunoId);
		notaNovaDTO.setMateriaId(materiaId);
		notaNovaDTO.setAnoLetivo(anoLetivo);
		notaNovaDTO.setNotaBim1(5);
		notaNovaDTO.setNotaBim2(5);
		notaNovaDTO.setNotaBim3(5);
		notaNovaDTO.setNotaBim4(5);

		String jsonBody = objectMapper.writeValueAsString(notaNovaDTO);
		ResultActions result = mockMvc.perform(post("/boletim/nota").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
	}

	@Test
	void verificaRetornoPostFrequencia() throws Exception {

		frequenciaNovaDTO.setAlunoId(alunoId);
		frequenciaNovaDTO.setMateriaId(materiaId);
		frequenciaNovaDTO.setAnoLetivo(anoLetivo);
		frequenciaNovaDTO.setQtdeFaltaBim1((short) 1);
		frequenciaNovaDTO.setQtdeFaltaBim2((short) 1);
		frequenciaNovaDTO.setQtdeFaltaBim3((short) 1);
		frequenciaNovaDTO.setQtdeFaltaBim4((short) 1);
		frequenciaNovaDTO.setQtdePresenca((short) 1);

		String jsonBody = objectMapper.writeValueAsString(frequenciaNovaDTO);
		ResultActions result = mockMvc.perform(post("/boletim/frequencia").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());

	}

}
