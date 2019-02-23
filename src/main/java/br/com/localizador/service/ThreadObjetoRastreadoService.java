package br.com.localizador.service;

import javax.naming.InitialContext;
import javax.naming.NamingException;


public class ThreadObjetoRastreadoService implements Runnable {

	private PosicaoService posicaoService;
	private String dados;
	
	public ThreadObjetoRastreadoService(String dados) {
		this.dados = dados;
	}
	
	@Override
	public void run() {
		try {
			this.posicaoService = (PosicaoService) new InitialContext().lookup("java:global/localizador/PosicaoService");
			this.posicaoService.salvar(this.dados);
		} catch (NamingException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}