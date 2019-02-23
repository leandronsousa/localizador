package br.com.localizador.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the loc_pessoa_fisica database table.
 * 
 */
@Entity
@Table(name="loc_pessoa_fisica")
public class PessoaFisica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="loc_pessoa_fisica_chave_generator", sequenceName="loc_seq_pessoa_fisica_chave", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="loc_pessoa_fisica_chave_generator")
	private Long chave;

	private String cpf;

	private String nome;

	public Long getChave() {
		return this.chave;
	}

	public void setChave(Long chave) {
		this.chave = chave;
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}