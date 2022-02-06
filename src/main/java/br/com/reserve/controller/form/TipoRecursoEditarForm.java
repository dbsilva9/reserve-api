package br.com.reserve.controller.form;

import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TipoRecursoEditarForm {

	@NotBlank(message = "Campo Id do Tipo de Recurso é obrigatório")
	@JMap
	private String id;

	@JMap
	private String nome;

	@JMap
	private String descricao;

	@JMap
	private String ativo;

}
