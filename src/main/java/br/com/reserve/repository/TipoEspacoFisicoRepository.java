package br.com.reserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.reserve.entity.TipoEspacoFisicoEntity;

@Repository
public interface TipoEspacoFisicoRepository extends JpaRepository<TipoEspacoFisicoEntity, String> {

}
