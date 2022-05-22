package com.meuboletim.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meuboletim.entities.Nota;

@Repository
public interface NotaRepository extends JpaRepository<Nota, UUID> {

	List<Nota> findByAlunoIdAndAnoLetivo(UUID alunoId, short anoLetivo);

	Optional<Nota> findFirstByAlunoIdAndMateriaIdAndAnoLetivo(UUID alunoId, UUID materiaId, short anoLetivo);
}