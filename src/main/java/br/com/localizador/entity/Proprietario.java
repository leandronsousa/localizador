package br.com.localizador.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the loc_proprietario database table.
 * 
 */
@Entity
@Table(name="loc_proprietario")
public class Proprietario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="loc_proprietario_chave_generator", sequenceName="loc_seq_proprietario_chave", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="loc_proprietario_chave_generator")
	private Long chave;

	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="chave_pessoa_fisica")
	private PessoaFisica pessoaFisica;

	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="chave_pessoa_juridica")
	private PessoaJuridica pessoaJuridica;

	//bi-directional many-to-one association to ObjetoRastreado
	@OneToMany(mappedBy="proprietario", cascade=CascadeType.ALL)
	private List<ObjetoRastreado> listaObjetoRastreado;

	//bi-directional many-to-one association to LocUsuario
	@OneToMany(mappedBy="proprietario", cascade=CascadeType.ALL)
	private List<Usuario> listaUsuario;
	
	@Transient
	private String tipoPessoa;

	public Long getChave() {
		return this.chave;
	}

	public void setChave(Long chave) {
		this.chave = chave;
	}

	public PessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	public PessoaJuridica getPessoaJuridica() {
		return pessoaJuridica;
	}

	public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}

	public List<ObjetoRastreado> getListaObjetoRastreado() {
		return this.listaObjetoRastreado;
	}

	public void setListaObjetoRastreado(List<ObjetoRastreado> listaObjetoRastreado) {
		this.listaObjetoRastreado = listaObjetoRastreado;
	}

	public List<Usuario> getListaUsuario() {
		return this.listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

}