package br.com.reserve.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@EqualsAndHashCode(of = "id")
@Entity(name = "perfil")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class PerfilEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "perfil_uuid")
	@GenericGenerator(name = "perfil_uuid", strategy = "uuid2")
	@Column(name = "id", columnDefinition = "VARCHAR(36)")
	private String id;

	@Column(name = "nome", length = 35, nullable = false)
	@ColumnTransformer(write = "upper(?)", read = "upper(nome)")
	private String nome;

}
