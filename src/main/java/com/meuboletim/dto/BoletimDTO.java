package com.meuboletim.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoletimDTO {

	private UUID materiaId;

	private short qtdeFaltaBim1;
	private short qtdeFaltaBim2;
	private short qtdeFaltaBim3;
	private short qtdeFaltaBim4;
	private short qtdePresenca;

	private float notaBim1;
	private float notaBim2;
	private float notaBim3;
	private float notaBim4;

}
