package com.meuboletim.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meuboletim.dto.FrequenciaDTO;
import com.meuboletim.dto.NotaDTO;
import com.meuboletim.services.BoletimService;

@SpringBootTest
@AutoConfigureMockMvc
public class BoletimControllerTest {

	private UUID alunoId;
	private short anoLetivo;
	private NotaDTO novaNotaDto;
	private NotaDTO novaNotaDto2;
	private FrequenciaDTO novaFreqDto;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BoletimService service;

	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
	void setup() {
		alunoId = UUID.randomUUID();
		anoLetivo = 2022;
		novaNotaDto = new NotaDTO(UUID.randomUUID(), alunoId, 2f, 3f, 4f, 5f, anoLetivo);
		novaNotaDto2 = new NotaDTO(UUID.randomUUID(), alunoId, 2f, 3f, 4f, 5f, anoLetivo);
		novaFreqDto = new FrequenciaDTO(alunoId, UUID.randomUUID(), (short) 2, (short) 3, (short) 4, (short) 5,
				(short) 10, anoLetivo);
	}

////////////////Testes GET Boletim/////////////////////////////////////////////////
	@Test
	void verificaRetornoGetBoletim() throws Exception {
		ResultActions result = mockMvc
				.perform(get("/boletim?alunoId={alunoId}&anoLetivo={anoLetivo}", alunoId, anoLetivo)
						.accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
		Mockito.verify(service, Mockito.times(1)).getBoletim(alunoId, anoLetivo);
	}

	@Test
	void verificaAlunoVazioENaoChamaBoletimService() throws Exception {
		ResultActions result = mockMvc
				.perform(get("/boletim?anoLetivo={anoLetivo}", anoLetivo).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isBadRequest());

		Assertions.assertEquals(result.andReturn().getResolvedException().getMessage(), "Informe o aluno");
		Mockito.verify(service, Mockito.times(0)).getBoletim(alunoId, anoLetivo);
	}

	@Test
	void verificaAnoVazioENaoChamaBoletimService() throws Exception {
		ResultActions result = mockMvc
				.perform(get("/boletim?alunoId={alunoId}", alunoId).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isBadRequest());
		Assertions.assertEquals(result.andReturn().getResolvedException().getMessage(), "Informe o ano");
		Mockito.verify(service, Mockito.times(0)).getBoletim(alunoId, anoLetivo);
	}

/////////////Testes POST nota//////////////////////////////////////////////////////
	@Test
	void verificaRetornoPostNota() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(novaNotaDto);
		ResultActions result = mockMvc.perform(post("/boletim/nota").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
		Mockito.verify(service, Mockito.times(1)).salvarNota(novaNotaDto);
	}

	@Test
	void verificaValidacaoMateriaPostNota() throws Exception {
		novaNotaDto.setMateriaId(null);
		String jsonBody = objectMapper.writeValueAsString(novaNotaDto);
		ResultActions result = mockMvc.perform(post("/boletim/nota").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isBadRequest());
		Assertions.assertEquals(result.andReturn().getResolvedException().getMessage(), "Matéria obrigatória");
		Mockito.verify(service, Mockito.times(0)).salvarNota(novaNotaDto);
	}

	@Test
	void verificaValidacaoAlunoPostNota() throws Exception {
		novaNotaDto.setAlunoId(null);
		String jsonBody = objectMapper.writeValueAsString(novaNotaDto);
		ResultActions result = mockMvc.perform(post("/boletim/nota").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isBadRequest());
		Assertions.assertEquals(result.andReturn().getResolvedException().getMessage(), "Aluno obrigatório");
		Mockito.verify(service, Mockito.times(0)).salvarNota(novaNotaDto);
	}

	@Test
	void verificaValidacaoAnoLetivoPostNota() throws Exception {
		novaNotaDto.setAnoLetivo(null);
		String jsonBody = objectMapper.writeValueAsString(novaNotaDto);
		ResultActions result = mockMvc.perform(post("/boletim/nota").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isBadRequest());
		Assertions.assertEquals(result.andReturn().getResolvedException().getMessage(), "Ano obrigatório");
		Mockito.verify(service, Mockito.times(0)).salvarNota(novaNotaDto);
	}

	@Test
	void verificaValidacaoNotaBim1PostNota() throws Exception {
		novaNotaDto.setNotaBim1(-1f);
		novaNotaDto2.setNotaBim1(11f);
		String jsonBody = objectMapper.writeValueAsString(novaNotaDto);
		String jsonBody2 = objectMapper.writeValueAsString(novaNotaDto2);

		ResultActions result = mockMvc.perform(post("/boletim/nota").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isBadRequest());
		ResultActions result2 = mockMvc.perform(post("/boletim/nota").content(jsonBody2)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result2.andExpect(status().isBadRequest());

		Assertions.assertEquals(result.andReturn().getResolvedException().getMessage(),
				"Valor não pode ser abaixo de zero");
		Assertions.assertEquals(result2.andReturn().getResolvedException().getMessage(),
				"Valor não pode ser acima de dez");

		Mockito.verify(service, Mockito.times(0)).salvarNota(novaNotaDto);
		Mockito.verify(service, Mockito.times(0)).salvarNota(novaNotaDto2);
	}

	@Test
	void verificaValidacaoNotaBim2PostNota() throws Exception {
		novaNotaDto.setNotaBim2(-1f);
		novaNotaDto2.setNotaBim2(11f);
		String jsonBody = objectMapper.writeValueAsString(novaNotaDto);
		String jsonBody2 = objectMapper.writeValueAsString(novaNotaDto2);

		ResultActions result = mockMvc.perform(post("/boletim/nota").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isBadRequest());
		ResultActions result2 = mockMvc.perform(post("/boletim/nota").content(jsonBody2)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result2.andExpect(status().isBadRequest());

		Assertions.assertEquals(result.andReturn().getResolvedException().getMessage(),
				"Valor não pode ser abaixo de zero");
		Assertions.assertEquals(result2.andReturn().getResolvedException().getMessage(),
				"Valor não pode ser acima de dez");

		Mockito.verify(service, Mockito.times(0)).salvarNota(novaNotaDto);
		Mockito.verify(service, Mockito.times(0)).salvarNota(novaNotaDto2);
	}

	@Test
	void verificaValidacaoNotaBim3PostNota() throws Exception {
		novaNotaDto.setNotaBim3(-1f);
		novaNotaDto2.setNotaBim3(11f);
		String jsonBody = objectMapper.writeValueAsString(novaNotaDto);
		String jsonBody2 = objectMapper.writeValueAsString(novaNotaDto2);

		ResultActions result = mockMvc.perform(post("/boletim/nota").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isBadRequest());
		ResultActions result2 = mockMvc.perform(post("/boletim/nota").content(jsonBody2)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result2.andExpect(status().isBadRequest());

		Assertions.assertEquals(result.andReturn().getResolvedException().getMessage(),
				"Valor não pode ser abaixo de zero");
		Assertions.assertEquals(result2.andReturn().getResolvedException().getMessage(),
				"Valor não pode ser acima de dez");

		Mockito.verify(service, Mockito.times(0)).salvarNota(novaNotaDto);
		Mockito.verify(service, Mockito.times(0)).salvarNota(novaNotaDto2);
	}

	@Test
	void verificaValidacaoNotaBim4PostNota() throws Exception {
		novaNotaDto.setNotaBim4(-1f);
		novaNotaDto2.setNotaBim4(11f);
		String jsonBody = objectMapper.writeValueAsString(novaNotaDto);
		String jsonBody2 = objectMapper.writeValueAsString(novaNotaDto2);

		ResultActions result = mockMvc.perform(post("/boletim/nota").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isBadRequest());
		ResultActions result2 = mockMvc.perform(post("/boletim/nota").content(jsonBody2)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result2.andExpect(status().isBadRequest());

		Assertions.assertEquals(result.andReturn().getResolvedException().getMessage(),
				"Valor não pode ser abaixo de zero");
		Assertions.assertEquals(result2.andReturn().getResolvedException().getMessage(),
				"Valor não pode ser acima de dez");

		Mockito.verify(service, Mockito.times(0)).salvarNota(novaNotaDto);
		Mockito.verify(service, Mockito.times(0)).salvarNota(novaNotaDto2);
	}

/////////////Testes POST frequencia////////////////////////////////////////////////////////////
	@Test
	void verificaRetornoPostFrequencia() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(novaFreqDto);
		ResultActions result = mockMvc.perform(post("/boletim/frequencia").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
		Mockito.verify(service, Mockito.times(1)).salvarFrequencia(novaFreqDto);
	}

	@Test
	void verificaValidacaoAlunoPostFrequencia() throws Exception {
		novaFreqDto.setAlunoId(null);
		String jsonBody = objectMapper.writeValueAsString(novaFreqDto);
		ResultActions result = mockMvc.perform(post("/boletim/frequencia").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isBadRequest());
		Assertions.assertEquals(result.andReturn().getResolvedException().getMessage(), "Aluno obrigatório");
		Mockito.verify(service, Mockito.times(0)).salvarFrequencia(novaFreqDto);
	}

	@Test
	void verificaValidacaoMateriaPostFrequencia() throws Exception {
		novaFreqDto.setMateriaId(null);
		String jsonBody = objectMapper.writeValueAsString(novaFreqDto);
		ResultActions result = mockMvc.perform(post("/boletim/frequencia").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isBadRequest());
		Assertions.assertEquals(result.andReturn().getResolvedException().getMessage(), "Matéria obrigatória");
		Mockito.verify(service, Mockito.times(0)).salvarFrequencia(novaFreqDto);
	}

	@Test
	void verificaValidacaoAnoLetivoPostFrequencia() throws Exception {
		novaFreqDto.setAnoLetivo(null);
		String jsonBody = objectMapper.writeValueAsString(novaFreqDto);
		ResultActions result = mockMvc.perform(post("/boletim/frequencia").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isBadRequest());
		Assertions.assertEquals(result.andReturn().getResolvedException().getMessage(), "Ano obrigatório");
		Mockito.verify(service, Mockito.times(0)).salvarFrequencia(novaFreqDto);
	}

	@Test
	void verificaValidacaoQtdeFrequenciaBim1PostFrequencia() throws Exception {
		novaFreqDto.setQtdeFaltaBim1((short) -1);
		String jsonBody = objectMapper.writeValueAsString(novaFreqDto);
		ResultActions result = mockMvc.perform(post("/boletim/frequencia").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isBadRequest());
		Assertions.assertEquals(result.andReturn().getResolvedException().getMessage(),
				"Valor não pode ser abaixo de zero");
		Mockito.verify(service, Mockito.times(0)).salvarFrequencia(novaFreqDto);
	}

	@Test
	void verificaValidacaoQtdeFrequenciaBim2PostFrequencia() throws Exception {
		novaFreqDto.setQtdeFaltaBim2((short) -1);
		String jsonBody = objectMapper.writeValueAsString(novaFreqDto);
		ResultActions result = mockMvc.perform(post("/boletim/frequencia").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isBadRequest());
		Assertions.assertEquals(result.andReturn().getResolvedException().getMessage(),
				"Valor não pode ser abaixo de zero");
		Mockito.verify(service, Mockito.times(0)).salvarFrequencia(novaFreqDto);
	}

	@Test
	void verificaValidacaoQtdeFrequenciaBim3PostFrequencia() throws Exception {
		novaFreqDto.setQtdeFaltaBim3((short) -1);
		String jsonBody = objectMapper.writeValueAsString(novaFreqDto);
		ResultActions result = mockMvc.perform(post("/boletim/frequencia").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isBadRequest());
		Assertions.assertEquals(result.andReturn().getResolvedException().getMessage(),
				"Valor não pode ser abaixo de zero");
		Mockito.verify(service, Mockito.times(0)).salvarFrequencia(novaFreqDto);
	}

	@Test
	void verificaValidacaoQtdeFrequenciaBim4PostFrequencia() throws Exception {
		novaFreqDto.setQtdeFaltaBim4((short) -1);
		String jsonBody = objectMapper.writeValueAsString(novaFreqDto);
		ResultActions result = mockMvc.perform(post("/boletim/frequencia").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isBadRequest());
		Assertions.assertEquals(result.andReturn().getResolvedException().getMessage(),
				"Valor não pode ser abaixo de zero");
		Mockito.verify(service, Mockito.times(0)).salvarFrequencia(novaFreqDto);
	}

	@Test
	void verificaValidacaoQtdePresencaPostFrequencia() throws Exception {
		novaFreqDto.setQtdeFaltaBim1((short) -1);
		String jsonBody = objectMapper.writeValueAsString(novaFreqDto);
		ResultActions result = mockMvc.perform(post("/boletim/frequencia").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isBadRequest());
		Assertions.assertEquals(result.andReturn().getResolvedException().getMessage(),
				"Valor não pode ser abaixo de zero");
		Mockito.verify(service, Mockito.times(0)).salvarFrequencia(novaFreqDto);
	}

}
