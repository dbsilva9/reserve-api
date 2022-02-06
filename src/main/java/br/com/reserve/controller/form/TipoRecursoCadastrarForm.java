package br.com.reserve.controller.form;

import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;

@Data
public class TipoRecursoCadastrarForm {

	@JMap
	private String nome;

	@JMap
	private String descricao;

	@JMap
	private String ativo;

}
