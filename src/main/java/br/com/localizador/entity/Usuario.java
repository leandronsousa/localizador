package br.com.localizador.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * The persistent class for the loc_usuario database table.
 * 
 */
@Entity
@Table(name="loc_usuario")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="loc_usuario_chave_generator", sequenceName="loc_seq_usuario_chave", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="loc_usuario_chave_generator")
	private Long chave;

	private String login;

	private String nome;
	
	private String senha;
	
	@Column(name="data_cadastro", updatable=false)
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="chave_perfil_usuario")
	private PerfilUsuario perfilUsuario;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="chave_proprietario")
	private Proprietario proprietario;

	public Usuario() {
	}

	public Long getChave() {
		return this.chave;
	}

	public void setChave(Long chave) {
		this.chave = chave;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public PerfilUsuario getPerfilUsuario() {
		return this.perfilUsuario;
	}

	public void setPerfilUsuario(PerfilUsuario PerfilUsuario) {
		this.perfilUsuario = PerfilUsuario;
	}

	public Proprietario getProprietario() {
		return this.proprietario;
	}

	public void setProprietario(Proprietario Proprietario) {
		this.proprietario = Proprietario;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || this.getClass() != obj.getClass()) return false;
		Usuario usuario = (Usuario) obj;
		return new EqualsBuilder().append(this.getLogin(), usuario.getLogin()).isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(31, 13).append(this.getLogin()).toHashCode();
	}

}