package br.com.reserve.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "recurso")
@EqualsAndHashCode(of = "id")
@Audited(targetAuditMode = RelationTargetAuditMode.AUDITED)
public class RecursoEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "recurso_uuid")
	@GenericGenerator(name = "recurso_uuid", strategy = "uuid2")
	@Column(name = "id", columnDefinition = "VARCHAR(36)")
	private String id;

	@Column(name = "nome", length = 100, nullable = false)
	@ColumnTransformer(write = "upper(?)", read = "upper(nome)")
	private String nome;
	
	@Column(name = "qtd_disponivel", nullable = false)
	private Integer quantidadeDisponivel;
	
	@Column(name = "ativo", nullable = false)
	private boolean ativo;
	
	@ManyToOne
	private TipoRecursoEntity tipoRecurso;
	
}
