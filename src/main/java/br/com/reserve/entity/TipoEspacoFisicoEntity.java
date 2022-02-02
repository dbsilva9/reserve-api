package br.com.reserve.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tipo_espaco_fisico")
@EqualsAndHashCode(of = "id")
public class TipoEspacoFisicoEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "tipo_espaco_fisico_uuid")
	@GenericGenerator(name = "tipo_espaco_fisico_uuid", strategy = "uuid2")
	@Column(name = "id", columnDefinition = "VARCHAR(36)")
	private String id;

	@Column(name = "nome", length = 100, nullable = false)
	@ColumnTransformer(write = "upper(?)", read = "upper(nome)")
	private String nome;
	
	@Column(name = "permite_reserva", nullable = false)
	private boolean permiteReserva;
	
	@Column(name = "permite_reserva_automatica", nullable = false)
	private boolean permiteReservaAutomatica;
	
	@Column(name = "ativo", nullable = false)
	private boolean ativo;

}

