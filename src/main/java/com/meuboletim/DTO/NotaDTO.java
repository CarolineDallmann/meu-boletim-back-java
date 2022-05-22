package com.meuboletim.DTO;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotaDTO {

	private UUID materiaId;
	private UUID alunoId;

	private float notaBim1;
	private float notaBim2;
	private float notaBim3;
	private float notaBim4;
	private short anoLetivo;
}
