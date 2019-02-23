package br.com.localizador.enums;

public enum ConstantesMensagem {

	REGISTRO_CADASTRADO_COM_SUCESSO("label.geral.registroCadastradoSucesso"),
	REGISTRO_ALTERADO_COM_SUCESSO("label.geral.registroAlteradoSucesso"),
	EDICAO_CANCELADA("label.geral.edicaoCancelada"),
	ERRO_VALORES_IGUAIS("label.geral.erroValoresIguais"),
	REGISTRO_EXCLUIDO_COM_SUCESSO("label.geral.registroExcluidoSucesso"),
	LOGIN_SUCESSO("label.geral.loginRealizadoSucesso"),
	LOGIN_ERRO("label.geral.loginRealizadoErro"),
	VERIFICADO_SUCESSO("label.geral.verificadoSucesso"),
	NENHUM_REGISTRO_ENCONTRADO("label.geral.nenhumRegistroEncontrado"),
	SENHA_NAO_INFORMADA("label.geral.senhaNaoInformada"),
	CPF_JA_CADASTRADO("label.geral.cpfJaCadastrado"),
	CPF_INVALIDO("label.geral.cpfInvalido"),
	CNPJ_JA_CADASTRADO("label.geral.cnpjJaCadastrado"),
	CNPJ_INVALIDO("label.geral.cnpjInvalido"),
	LOGIN_JA_CADASTRADO("label.geral.loginJaCadastrado"),
	CADASTRO_INVALIDO("label.geral.cadastroInvalido"),
	ERRO_INESPERADO("label.geral.erroInesperado");
	
	private ConstantesMensagem(String key){
		this.setKey(key);
	}
	
	private String key;

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	
}
