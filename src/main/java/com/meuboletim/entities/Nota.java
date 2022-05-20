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
public class Nota {

	@Id
	private UUID id;
	private UUID materiaId;
	private UUID alunoId;

	private float notaBim1;
	private float notaBim2;
	private float notaBim3;
	private float notaBim4;
	private short anoLetivo;

}
