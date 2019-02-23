package br.com.localizador.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the loc_pessoa_juridica database table.
 * 
 */
@Entity
@Table(name="loc_pessoa_juridica")
public class PessoaJuridica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="loc_pessoa_juridica_chave_generator", sequenceName="loc_seq_pessoa_juridica_chave", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="loc_pessoa_juridica_chave_generator")
	private Long chave;

	private String cnpj;

	@Column(name="nome_fantasia")
	private String nomeFantasia;

	@Column(name="razao_social")
	private String razaoSocial;

	public PessoaJuridica() {
	}

	public Long getChave() {
		return this.chave;
	}

	public void setChave(Long chave) {
		this.chave = chave;
	}

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNomeFantasia() {
		return this.nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return this.razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

}