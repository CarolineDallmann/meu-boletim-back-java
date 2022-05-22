package com.meuboletim.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Nota {

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(unique = true, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	private UUID materiaId;
	private UUID alunoId;

	private float notaBim1;
	private float notaBim2;
	private float notaBim3;
	private float notaBim4;
	private short anoLetivo;

}
