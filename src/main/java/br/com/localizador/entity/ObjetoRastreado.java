package br.com.localizador.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.google.gson.annotations.Expose;


/**
 * The persistent class for the loc_objeto_rastreado database table.
 * 
 */
@Entity
@Table(name="loc_objeto_rastreado")
public class ObjetoRastreado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String imei;

	private Integer ddd;

	@Expose
	private String nome;

	private BigInteger telefone;

	@Expose
	@OneToMany(mappedBy="objetoRastreado", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@Column(updatable=false)
	private List<Posicao> listaPosicao;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="chave_proprietario")
	private Proprietario proprietario;

	public ObjetoRastreado() {
	}

	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Integer getDdd() {
		return this.ddd;
	}

	public void setDdd(Integer ddd) {
		this.ddd = ddd;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigInteger getTelefone() {
		return this.telefone;
	}

	public void setTelefone(BigInteger telefone) {
		this.telefone = telefone;
	}

	public List<Posicao> getListaPosicao() {
		return this.listaPosicao;
	}

	public void setListaPosicao(List<Posicao> listaPosicao) {
		this.listaPosicao = listaPosicao;
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
		ObjetoRastreado objetoRastreado = (ObjetoRastreado) obj;
		return new EqualsBuilder().append(this.getImei(), objetoRastreado.getImei()).isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(31, 13).append(this.getImei()).toHashCode();
	}

}