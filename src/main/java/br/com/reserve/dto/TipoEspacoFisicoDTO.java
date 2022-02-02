package br.com.reserve.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.googlecode.jmapper.annotations.JMap;

import br.com.reserve.validacao.OnCreate;
import br.com.reserve.validacao.OnUpdate;
import lombok.Data;

@Data
public class TipoEspacoFisicoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@JMap
	@Null(groups = OnCreate.class)
	@NotNull(groups = OnUpdate.class, message = "O campo Id é obrigatório")
	private String id;

	@JMap
	@NotBlank(groups = { OnCreate.class }, message = "O campo Nome é obrigatório")
	private String nome;

	@JMap
	@NotNull(groups = OnCreate.class, message = "O campo Permite Reserva é obrigatório")
	private String permiteReserva;

	@JMap
	@NotNull(groups = OnCreate.class, message = "O campo Permite Reserva Automatica é obrigatório")
	private String permiteReservaAutomatica;

	@JMap
	@NotNull(groups = OnCreate.class, message = "O campo Ativo é obrigatório")
	private String ativo;

}
