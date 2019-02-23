package br.com.localizador.enums;

public enum TipoObjetoRastreadoEnum {

	CARRO("Carro");
	
	private String descricao;
	
	private TipoObjetoRastreadoEnum(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
}
