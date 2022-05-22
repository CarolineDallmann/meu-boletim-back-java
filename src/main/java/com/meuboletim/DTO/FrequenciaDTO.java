package com.meuboletim.DTO;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrequenciaDTO {

	private UUID alunoId;
	private UUID materiaId;

	private short qtdeFaltaBim1;
	private short qtdeFaltaBim2;
	private short qtdeFaltaBim3;
	private short qtdeFaltaBim4;
	private short anoLetivo;
	private short qtdePresenca;
}
