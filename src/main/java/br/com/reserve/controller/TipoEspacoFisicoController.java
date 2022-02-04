package br.com.reserve.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.reserve.dto.TipoEspacoFisicoDTO;
import br.com.reserve.exception.BusinessException;
import br.com.reserve.exception.NotFoundException;
import br.com.reserve.response.Response;
import br.com.reserve.service.TipoEspacoFisicoService;
import br.com.reserve.validacao.OnCreate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;

@Api(value = "Operação com Tipo Espaço Físico")
@RestController
@RequestMapping("/tiposEspacoFisico")
@Log4j2
@Validated
public class TipoEspacoFisicoController {

	private final TipoEspacoFisicoService service;

	public TipoEspacoFisicoController(TipoEspacoFisicoService service) {
		this.service = service;
	}

	@Validated(OnCreate.class)
	@ApiOperation(value = "Cadastra um tipo espaço físico", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Tipo espaço físico cadastrado"),
			@ApiResponse(code = 404, message = "Tipo espaço físico não foi cadastrado") })
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<TipoEspacoFisicoDTO>> create(@Valid @RequestBody TipoEspacoFisicoDTO form,
			BindingResult result) throws BusinessException {

		Response<TipoEspacoFisicoDTO> response = new Response<TipoEspacoFisicoDTO>();

		try {

			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}

			TipoEspacoFisicoDTO area = service.save(form);

			response.setData(area);

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			log.error("Não foi possível executar a ação.", e);
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@ApiOperation(value = "Obtém tipo espaço físico pelo ID", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Tipo espaço físico encontrado"),
			@ApiResponse(code = 404, message = "Tipo espaço físico não encontrado") })
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<TipoEspacoFisicoDTO>> findById(@PathVariable("id") String id) throws NotFoundException{

		Response<TipoEspacoFisicoDTO> response = new Response<TipoEspacoFisicoDTO>();
		
		try {

			TipoEspacoFisicoDTO dto = service.findAreaById(id);
			response.setData(dto);

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			log.error("Não foi possível executar a ação.", e);
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

	@ApiOperation(value = "Obtém todos os tipo espaço físicos", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lista de tipo espaço físicos encontrada"),
			@ApiResponse(code = 404, message = "Lista de tipo espaço físicos vazia") })
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Page<TipoEspacoFisicoDTO>>> findAll(
			@PageableDefault(sort = "nome", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {

		Response<Page<TipoEspacoFisicoDTO>> response = new Response<Page<TipoEspacoFisicoDTO>>();
		try {

			Page<TipoEspacoFisicoDTO> lista = service.findAll(paginacao);
			response.setData(lista);

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			log.error("Não foi possível executar a ação.", e);
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}


}
