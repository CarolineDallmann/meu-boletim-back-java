package com.meuboletim.DTO;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoletimDTO {

	private UUID materiaId;

	private float qtdeFaltaBim1;
	private float qtdeFaltaBim2;
	private float qtdeFaltaBim3;
	private float qtdeFaltaBim4;

	private float notaBim1;
	private float notaBim2;
	private float notaBim3;
	private float notaBim4;

}
