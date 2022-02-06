package br.com.reserve.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(of = "id")
@Entity(name = "usuario")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "uk_email_usuario"), @UniqueConstraint(columnNames = "login", name = "uk_login_usuario")})
@Audited(targetAuditMode = RelationTargetAuditMode.AUDITED)
public class UsuarioEntity {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "usuario_uuid")
  @GenericGenerator(name = "usuario_uuid", strategy = "uuid2")
  @Column(name = "id", columnDefinition = "VARCHAR(36)")
  private String id;

  @Column(name = "nome", length = 100, nullable = false)
  @ColumnTransformer(write = "upper(?)", read = "upper(nome)")
  private String nome;

  @Column(name = "cpf", length = 11, nullable = true)
  private String cpf;

  @Column(name = "nascimento", nullable = true)
  @Temporal(TemporalType.DATE)
  private Date nascimento;

  @Column(name = "login", length = 26, nullable = false)
  private String login;

  @Column(name = "senha", length = 256, nullable = false)
  private String senha;

  @Column(name = "email", length = 100, nullable = false)
  private String email;

  @Column(name = "telefone", length = 10, nullable = true)
  private String telefone;

  @Column(name = "celular", length = 11, nullable = true)
  private String celular;

  @Column(name = "ativo", nullable = false, columnDefinition = "bit default 1")
  private boolean ativo;

  @ManyToMany(fetch = FetchType.EAGER)
  private List<PerfilEntity> perfis;

}
