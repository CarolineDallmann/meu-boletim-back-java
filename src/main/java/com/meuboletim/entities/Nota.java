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
public class Nota {

	@Id
	@Column(unique = true, nullable = false)
	private String id;
	private UUID materiaId;
	private UUID alunoId;

	private float notaBim1;
	private float notaBim2;
	private float notaBim3;
	private float notaBim4;
	private short anoLetivo;

	public Nota(String id) {
		super();
		this.id = id;
	}

}
