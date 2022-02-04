package br.com.reserve.service;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.googlecode.jmapper.JMapper;

import br.com.reserve.dto.TipoEspacoFisicoDTO;
import br.com.reserve.entity.TipoEspacoFisicoEntity;
import br.com.reserve.exception.BusinessException;
import br.com.reserve.exception.NotFoundException;
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
	
	public TipoEspacoFisicoDTO findAreaById(String id) throws NotFoundException {
		return mapperDTO.getDestination(verificarSeExisteAreaPorId(id));
	}
	
	private TipoEspacoFisicoEntity verificarSeExisteAreaPorId(String id) throws NotFoundException {
		return repository.findById(id)
				.orElseThrow(()-> new NotFoundException(String.format(Mensagens.REGISTRO_NAO_ENCONTRADO, id)));
	}

	public Page<TipoEspacoFisicoDTO> findAll(Pageable paginacao) throws NotFoundException {
		Page<TipoEspacoFisicoEntity> lista = repository.findAll(paginacao);

		return lista.map(mapperDTO::getDestination);
	}

}
