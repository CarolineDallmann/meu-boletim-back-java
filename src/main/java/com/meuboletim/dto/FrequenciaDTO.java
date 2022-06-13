package com.meuboletim.dto;

import java.util.UUID;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrequenciaDTO {

	@NotNull(message = "Aluno obrigatório")
	private UUID alunoId;

	@NotNull(message = "Matéria obrigatória")
	private UUID materiaId;

	@Min(value = 0, message = "Valor não pode ser abaixo de zero")
	private short qtdeFaltaBim1;

	@Min(value = 0, message = "Valor não pode ser abaixo de zero")
	private short qtdeFaltaBim2;

	@Min(value = 0, message = "Valor não pode ser abaixo de zero")
	private short qtdeFaltaBim3;

	@Min(value = 0, message = "Valor não pode ser abaixo de zero")
	private short qtdeFaltaBim4;

	@Min(value = 0, message = "Valor não pode ser abaixo de zero")
	private short qtdePresenca;

	@NotNull(message = "Ano obrigatório")
	private Short anoLetivo;

}
