package br.com.localizador.infra;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;


@Singleton
@Startup
public class SocketInit {
	
	private ThreadServidorSocket servidorSocket;
	private Thread thread;
	
	@PostConstruct
	private void init() {
		this.servidorSocket = new ThreadServidorSocket();
		this.thread = new Thread(servidorSocket);
		this.thread.start();
		System.out.println("Socket Iniciado!");
	}
	
	@PreDestroy
	private void destroy() {
		this.servidorSocket.finalizarSocket();
		System.out.println("Socket finalizado!");
	}
	
}
