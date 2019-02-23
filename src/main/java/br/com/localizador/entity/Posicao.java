package br.com.localizador.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.google.gson.annotations.Expose;


/**
 * The persistent class for the loc_posicao database table.
 * 
 */
@Entity
@Table(name="loc_posicao")
public class Posicao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Expose
	@Id
	@SequenceGenerator(name="loc_posicao_chave_generator", sequenceName="loc_seq_posicao_chave", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="loc_posicao_chave_generator")
	private Long chave;
	
	@Expose
	@Column(name="data_hora", updatable=false)
	private Date dataHora;
	
	@Expose
	@Column(updatable=false)
	private BigDecimal latitude;

	@Column(name="hemisferio_latitude", updatable=false)
	private Character hemisferioLat;

	@Expose
	@Column(updatable=false)
	private BigDecimal longitude;
	
	@Column(name="hemisferio_longitude", updatable=false)
	private Character hemisferioLtn;
	
	@Expose
	@Column(updatable=false)
	private String endereco;
	
	@Column(updatable=false)
	private BigInteger telefone;

	@Expose
	@Column(updatable=false)
	private BigDecimal velocidade;

	//bi-directional many-to-one association to ObjetoRastreado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="imei_objeto_rastreado")
	private ObjetoRastreado objetoRastreado;

	public Posicao() {
	}
	
	public Long getChave() {
		return this.chave;
	}

	public void setChave(Long chave) {
		this.chave = chave;
	}

	public Date getDataHora() {
		return this.dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getVelocidade() {
		return this.velocidade;
	}

	public void setVelocidade(BigDecimal velocidade) {
		this.velocidade = velocidade;
	}

	public ObjetoRastreado getObjetoRastreado() {
		return this.objetoRastreado;
	}

	public void setObjetoRastreado(ObjetoRastreado ObjetoRastreado) {
		this.objetoRastreado = ObjetoRastreado;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Character getHemisferioLat() {
		return hemisferioLat;
	}

	public void setHemisferioLat(Character hemisferioLat) {
		this.hemisferioLat = hemisferioLat;
	}

	public Character getHemisferioLtn() {
		return hemisferioLtn;
	}

	public void setHemisferioLtn(Character hemisferioLtn) {
		this.hemisferioLtn = hemisferioLtn;
	}

	public BigInteger getTelefone() {
		return telefone;
	}

	public void setTelefone(BigInteger telefone) {
		this.telefone = telefone;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || this.getClass() != obj.getClass()) return false;
		Posicao posicao = (Posicao) obj;
		return new EqualsBuilder().append(this.getLatitude().doubleValue(), posicao.getLatitude().doubleValue())
				.append(this.getLongitude().doubleValue(), posicao.getLongitude().doubleValue()).isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(31, 13).append(this.getLatitude().doubleValue())
				.append(this.getLongitude().doubleValue()).toHashCode();
	}

}