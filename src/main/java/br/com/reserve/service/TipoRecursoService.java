package br.com.reserve.service;

import br.com.reserve.controller.dto.TipoRecursoDto;
import br.com.reserve.controller.form.TipoRecursoCadastrarForm;
import br.com.reserve.controller.form.TipoRecursoEditarForm;
import br.com.reserve.entity.TipoRecursoEntity;
import br.com.reserve.exception.BusinessException;
import br.com.reserve.exception.NotFoundException;
import br.com.reserve.repository.TipoRecursoRepository;
import br.com.reserve.util.Mensagens;
import com.google.common.base.Strings;
import com.googlecode.jmapper.JMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;



@Service
public class TipoRecursoService {

	private final JMapper<TipoRecursoDto, TipoRecursoEntity> mapperDto;

	private final JMapper<TipoRecursoEntity, TipoRecursoCadastrarForm> mapperCadastrarForm;

	private final JMapper<TipoRecursoEntity, TipoRecursoEditarForm> mapperEditarForm;

	@Autowired
	private TipoRecursoRepository repository;

	public TipoRecursoService() {
		mapperDto = new JMapper<>(TipoRecursoDto.class, TipoRecursoEntity.class);
		mapperCadastrarForm = new JMapper<>(TipoRecursoEntity.class, TipoRecursoCadastrarForm.class);
		mapperEditarForm = new JMapper<>(TipoRecursoEntity.class, TipoRecursoEditarForm.class);
	}

	public TipoRecursoDto findById(String id) throws NotFoundException {
		
		TipoRecursoEntity entity = getById(id);

		return mapperDto.getDestination(entity);

	}

	public Page<TipoRecursoDto> findAll(Pageable paginacao) throws NotFoundException {
		Page<TipoRecursoEntity> lista = repository.findAll(paginacao);

		if (lista.isEmpty()) {
			throw new NotFoundException(Mensagens.NENHUM_REGISTRO_NO_BANCO);
		}

		return lista.map(mapperDto::getDestination);
	}

	@Transactional
	public TipoRecursoDto save(TipoRecursoCadastrarForm form, String usuarioAutenticado) throws BusinessException {

		verificaSeExisteTipoRecursoPorNome(form.getNome());

		var entity = mapperCadastrarForm.getDestination(form);

		entity = repository.save(entity);

		return mapperDto.getDestination(entity);
	}

	@Transactional
	public TipoRecursoDto update(TipoRecursoEditarForm form, String usuarioAutenticado)
			throws NotFoundException, IllegalArgumentException, BusinessException {

		TipoRecursoEntity oldEntity = getById(form.getId());
		
		if(!form.getNome().equals(oldEntity.getNome())) {
			verificaSeExisteTipoRecursoPorNome(form.getNome());
		}

		var entityForm = mapperEditarForm.getDestination(form);

		oldEntity.setNome(entityForm.getNome());
		oldEntity.setDescricao(entityForm.getDescricao());
		oldEntity.setAtivo(entityForm.isAtivo());

		repository.save(oldEntity);

		return mapperDto.getDestination(oldEntity);

	}

	public void delete(String id) throws NotFoundException {

		TipoRecursoEntity entity = getById(id);
		
		repository.delete(entity);
		
	}

	public Page<TipoRecursoDto> findByFilter(Pageable paginacao, String nome, String ativo) throws NotFoundException {

		Page<TipoRecursoEntity> lista = repository.findByFilter(
				Strings.isNullOrEmpty(nome) ? null : "%" + nome + "%",
				Strings.isNullOrEmpty(ativo) ? null : Boolean.parseBoolean(ativo), 
				paginacao);

		return lista.map(mapperDto::getDestination);
	}

	public List<TipoRecursoDto> getAll() {
		
		return repository.findAll().stream()
				.map(mapperDto::getDestination)
				.collect(Collectors.toList());
	}

	
	private List<TipoRecursoEntity> verificaSeExisteTipoRecursoPorNome(String nome) throws BusinessException{
		
		var listTipoRecurso = repository.findByNome(nome);
		
		if (!listTipoRecurso.isEmpty()) {
			throw new BusinessException(String.format(Mensagens.REGISTRO_JA_CADASTRADO, "nome"));
		}
		
		return listTipoRecurso;
		
	}
	
	private TipoRecursoEntity getById(String id) throws NotFoundException {
		TipoRecursoEntity entity = repository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format(Mensagens.REGISTRO_NAO_ENCONTRADO, id)));
		return entity;
	}
	
}
