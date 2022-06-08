package com.meuboletim.DTO;

import java.util.UUID;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotaDTO {

	@NotNull(message = "Matéria obrigatória")
	private UUID materiaId;

	@NotNull(message = "Aluno obrigatório")
	private UUID alunoId;

	@Min(value = 0, message = "Valor não pode ser abaixo de zero")
	@Max(value = 10, message = "Valor não pode ser acima de dez")
	private float notaBim1;

	@Min(value = 0, message = "Valor não pode ser abaixo de zero")
	@Max(value = 10, message = "Valor não pode ser acima de dez")
	private float notaBim2;

	@Min(value = 0, message = "Valor não pode ser abaixo de zero")
	@Max(value = 10, message = "Valor não pode ser acima de dez")
	private float notaBim3;

	@Min(value = 0, message = "Valor não pode ser abaixo de zero")
	@Max(value = 10, message = "Valor não pode ser acima de dez")
	private float notaBim4;

	@NotNull(message = "Ano obrigatório")
	private Short anoLetivo;
}
