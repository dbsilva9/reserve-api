package br.com.reserve.repository;

import br.com.reserve.entity.TipoRecursoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoRecursoRepository extends JpaRepository<TipoRecursoEntity, String>{

	@Query(
			"select a from tipo_recurso a "
			+ "where "
			+ "(upper(a.nome) like upper(:nome) or :nome is null) and "
			+ "(a.ativo = :ativo or :ativo is null) "
			)
	Page<TipoRecursoEntity> findByFilter(
			@Param("nome") String nome, 
			@Param("ativo") Boolean ativo, 
			Pageable paginacao);
	
	List<TipoRecursoEntity> findByNome(String nome);

}
