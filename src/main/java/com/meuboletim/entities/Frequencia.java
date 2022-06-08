package com.meuboletim.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Frequencia {

	@Id
	@Column(unique = true, nullable = false)
	private String id;
	private UUID alunoId;
	private UUID materiaId;

	private short qtdeFaltaBim1;
	private short qtdeFaltaBim2;
	private short qtdeFaltaBim3;
	private short qtdeFaltaBim4;
	private short anoLetivo;
	private short qtdePresenca;

	public Frequencia(String id) {
		super();
		this.id = id;
	}
}
