package br.com.reserve.controller.dto;

import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;

import java.io.Serializable;

@Data
public class TipoRecursoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JMap
	private String id;

	@JMap
	private String nome;

	@JMap
	private String descricao;

	@JMap
	private String ativo;

}
