package br.com.reserve.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.googlecode.jmapper.JMapper;

import br.com.reserve.dto.TipoEspacoFisicoDTO;
import br.com.reserve.entity.TipoEspacoFisicoEntity;
import br.com.reserve.exception.BusinessException;
import br.com.reserve.repository.TipoEspacoFisicoRepository;
import br.com.reserve.util.Mensagens;

@Service
public class TipoEspacoFisicoService {

	private final TipoEspacoFisicoRepository repository;

	private final JMapper<TipoEspacoFisicoDTO, TipoEspacoFisicoEntity> mapperDTO;
	private final JMapper<TipoEspacoFisicoEntity, TipoEspacoFisicoDTO> mapperEntity;

	public TipoEspacoFisicoService(TipoEspacoFisicoRepository repository) {
		this.repository = repository;
		mapperDTO = new JMapper<>(TipoEspacoFisicoDTO.class, TipoEspacoFisicoEntity.class);
		mapperEntity = new JMapper<>(TipoEspacoFisicoEntity.class, TipoEspacoFisicoDTO.class);
	}

	@Transactional
	public TipoEspacoFisicoDTO save(TipoEspacoFisicoDTO form) throws BusinessException {

		verificarSeExisteAreaPorNome(form.getNome());

		var entity = mapperEntity.getDestination(form);

		entity = repository.save(entity);

		return mapperDTO.getDestination(entity);
	}

	private void verificarSeExisteAreaPorNome(String nome) throws BusinessException {
		var existeTipoEspacoFisico = repository.existsByNomeIgnoreCase(nome);
		if (existeTipoEspacoFisico) {
			throw new BusinessException(String.format(Mensagens.REGISTRO_JA_CADASTRADO, "Nome"));
		}
	}

}
