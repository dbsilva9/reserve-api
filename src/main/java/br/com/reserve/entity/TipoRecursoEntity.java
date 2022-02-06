package br.com.reserve.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
@Entity(name = "tipo_recurso")
@EqualsAndHashCode(of = "id")
@Audited(targetAuditMode = RelationTargetAuditMode.AUDITED)
public class TipoRecursoEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "tipo_recurso_uuid")
	@GenericGenerator(name = "tipo_recurso_uuid", strategy = "uuid2")
	@Column(name = "id", columnDefinition = "VARCHAR(36)")
	private String id;

	@Column(name = "nome", length = 100, nullable = false)
	@ColumnTransformer(write = "upper(?)", read = "upper(nome)")
	private String nome;
	
	@Column(name = "descricao", length = 300, nullable = true)
	@ColumnTransformer(write = "upper(?)", read = "upper(descricao)")
	private String descricao;

	@Column(name = "ativo", nullable = false)
	private boolean ativo;
	
	@OneToMany(mappedBy = "tipoRecurso")
	private List<RecursoEntity> recursos;

}
