package br.com.localizador.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the loc_perfil_usuario database table.
 * 
 */
@Entity
@Table(name="loc_perfil_usuario")
public class PerfilUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long chave;

	private String nome;

	//bi-directional many-to-one association to LocUsuario
	@OneToMany(fetch=FetchType.LAZY, mappedBy="perfilUsuario")
	private List<Usuario> listaUsuario;

	public PerfilUsuario() {
	}

	public Long getChave() {
		return this.chave;
	}

	public void setChave(Long chave) {
		this.chave = chave;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Usuario> getListaUsuario() {
		return this.listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

}