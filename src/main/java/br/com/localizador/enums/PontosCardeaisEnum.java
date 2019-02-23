package br.com.localizador.enums;


public enum PontosCardeaisEnum {

	LESTE ('E'),
	OESTE ('W'),
	NORTE ('N'),
	SUL ('S');
	
	private PontosCardeaisEnum(Character abreviacao) {
		this.abreviacao = abreviacao;
	}
	
	private Character abreviacao;

	public Character getAbreviacao() {
		return abreviacao;
	}

	public void setAbreviacao(Character abreviacao) {
		this.abreviacao = abreviacao;
	}
	
}