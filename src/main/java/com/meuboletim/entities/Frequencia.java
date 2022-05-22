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
public class Frequencia {

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(unique = true, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	private UUID alunoId;
	private UUID materiaId;

	private short qtdeFaltaBim1;
	private short qtdeFaltaBim2;
	private short qtdeFaltaBim3;
	private short qtdeFaltaBim4;
	private short anoLetivo;
	private short qtdePresenca;
}
