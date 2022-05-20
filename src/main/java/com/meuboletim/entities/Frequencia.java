package com.meuboletim.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Frequencia {

	@Id
	private UUID id;
	private UUID alunoId;
	private UUID materiaId;

	private float qtdeFaltaBim1;
	private float qtdeFaltaBim2;
	private float qtdeFaltaBim3;
	private float qtdeFaltaBim4;
	private short anoLetivo;
}
