package com.meuboletim.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meuboletim.entities.Frequencia;

@Repository
public interface FrequenciaRepository extends JpaRepository<Frequencia, UUID> {

	List<Frequencia> findByAlunoIdAndAnoLetivo(UUID alunoId, short anoLetivo);

	Optional<Frequencia> findFirstByAlunoIdAndMateriaIdAndAnoLetivo(UUID alunoId, UUID materiaId, short anoLetivo);
}