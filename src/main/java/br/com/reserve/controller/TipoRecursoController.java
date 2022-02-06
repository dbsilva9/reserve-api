package br.com.reserve.controller;

import br.com.reserve.controller.dto.TipoRecursoDto;
import br.com.reserve.controller.form.TipoRecursoCadastrarForm;
import br.com.reserve.controller.form.TipoRecursoEditarForm;
import br.com.reserve.entity.UsuarioEntity;
import br.com.reserve.response.Response;
import br.com.reserve.service.TipoRecursoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Api(value = "Operação com Tipos de Recurso")
@RestController
@RequestMapping("/tiposRecurso")
@Log4j2
public class TipoRecursoController {

  @Autowired
  private TipoRecursoService service;

  @ApiOperation(value = "Obtém tipo espaço físico pelo ID", response = ResponseEntity.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Área encontrado"), @ApiResponse(code = 404, message = "tipo espaço físico não encontrado")})
  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Response<TipoRecursoDto>> findById(@PathVariable("id") String id) {

    Response<TipoRecursoDto> response = new Response<>();

    try {

      TipoRecursoDto dto = service.findById(id);
      response.setData(dto);

      return ResponseEntity.ok(response);

    } catch (Exception e) {
      log.error("Não foi possível executar a ação.", e);
      response.getErrors().add(e.getMessage());
      return ResponseEntity.badRequest().body(response);
    }
  }

  @ApiOperation(value = "Obtém todos os tipo espaço físicos", response = ResponseEntity.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Lista de tipo espaço físicos encontrada"), @ApiResponse(code = 404, message = "Lista de tipo espaço físicos vazia")})
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Response<Page<TipoRecursoDto>>> findAll(@PageableDefault(sort = "nome", direction = Direction.DESC) Pageable paginacao) {

    Response<Page<TipoRecursoDto>> response = new Response<>();
    try {

      Page<TipoRecursoDto> lista = service.findAll(paginacao);
      response.setData(lista);

      return ResponseEntity.ok(response);

    } catch (Exception e) {
      log.error("Não foi possível executar a ação.", e);
      response.getErrors().add(e.getMessage());
      return ResponseEntity.badRequest().body(response);
    }
  }

  @Transactional
  @ApiOperation(value = "Atualiza Area", response = ResponseEntity.class)
  @ApiResponses(value = {@ApiResponse(code = 202, message = "Área atualizado"), @ApiResponse(code = 404, message = "Área que se deseja atualizar não foi encontrado")})
  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Response<TipoRecursoDto>> update(@Valid @RequestBody TipoRecursoEditarForm form, BindingResult result) {

    Response<TipoRecursoDto> response = new Response<>();

    try {

      if (result.hasErrors()) {
        result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(response);
      }

      UsuarioEntity usuarioAutenticado = new UsuarioEntity();

      TipoRecursoDto area = service.update(form, usuarioAutenticado.getId());
      response.setData(area);

      return ResponseEntity.accepted().body(response);

    } catch (Exception e) {
      log.error("Não foi possível executar a ação.", e);
      response.getErrors().add(e.getMessage());
      return ResponseEntity.badRequest().body(response);
    }
  }

  @Transactional
  @ApiOperation(value = "Cadastra um tipo espaço físico e retorna a URI do Área", response = ResponseEntity.class)
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Área cadastrado"), @ApiResponse(code = 404, message = "Área não foi cadastrado")})
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Response<TipoRecursoDto>> create(@Valid @RequestBody TipoRecursoCadastrarForm form, BindingResult result) {

    Response<TipoRecursoDto> response = new Response<>();

    try {

      if (result.hasErrors()) {
        result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(response);
      }

      TipoRecursoDto tipoRecurso = service.save(form, new UsuarioEntity().getId());

      URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/tiposRecurso/{id}").buildAndExpand(tipoRecurso.getId()).toUri();

      response.setData(tipoRecurso);

      return ResponseEntity.created(uri).body(response);

    } catch (Exception e) {
      log.error("Não foi possível executar a ação.", e);
      response.getErrors().add(e.getMessage());
      return ResponseEntity.badRequest().body(response);
    }
  }

  @ApiOperation(value = "Apaga uma tipo espaço físico", response = ResponseEntity.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Área apagada"), @ApiResponse(code = 404, message = "O tipo espaço físico se tentou apagar não foi encontrado")})
  @DeleteMapping(path = "/{id}")
  @Transactional
  public ResponseEntity<Response<TipoRecursoDto>> delete(@PathVariable("id") String id) {

    Response<TipoRecursoDto> response = new Response<>();

    try {

      service.delete(id);

    } catch (Exception e) {
      log.error("Não foi possível executar a ação.", e);
      response.getErrors().add(e.getMessage());
      return ResponseEntity.badRequest().body(response);
    }

    return ResponseEntity.ok(response);
  }

  @ApiOperation(value = "Obtém todos os tipo espaço físicos por filtro", response = ResponseEntity.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Lista de tipo espaço físicos encontrada"), @ApiResponse(code = 404, message = "Lista de tipo espaço físico vazia")})
  @GetMapping(path = "/findByFilter", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Response<Page<TipoRecursoDto>>> findByFilter(@RequestParam(required = false) String nome, @RequestParam(required = false) String ativo,
    @PageableDefault(sort = "nome", direction = Direction.ASC) Pageable paginacao) {

    Response<Page<TipoRecursoDto>> response = new Response<>();

    try {
      Page<TipoRecursoDto> areas = service.findByFilter(paginacao, nome, ativo);
      response.setData(areas);

      return ResponseEntity.ok(response);

    } catch (Exception e) {
      log.error("Não foi possível executar a ação.", e);
      response.getErrors().add(e.getMessage());
      return ResponseEntity.badRequest().body(response);
    }
  }

  @ApiOperation(value = "Obtém todos os tipos recurso", response = ResponseEntity.class)
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Lista de tipos recursos encontrada")})
  @GetMapping(path = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Response<List<TipoRecursoDto>>> getAll() {

    Response<List<TipoRecursoDto>> response = new Response<>();
    try {

      List<TipoRecursoDto> lista = service.getAll();
      response.setData(lista);

      return ResponseEntity.ok(response);

    } catch (Exception e) {
      log.error("Não foi possível executar a ação.", e);
      response.getErrors().add(e.getMessage());
      return ResponseEntity.badRequest().body(response);
    }
  }

}
