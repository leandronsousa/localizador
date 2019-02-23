package br.com.localizador.infra;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import br.com.localizador.service.ThreadObjetoRastreadoService;

public class ThreadServidorSocket implements Runnable {
	
	private InputStream inputStream;
	private OutputStream outputStream;
	private Socket socket;
	private ServerSocket serverSocket;

	private void iniciarSocket() {
		try {
			this.serverSocket = new ServerSocket(7000);
			while (true) {
				this.socket = this.serverSocket.accept();
				
				this.inputStream = this.socket.getInputStream();
				this.outputStream = this.socket.getOutputStream();
				byte[] buffer = new byte[this.socket.getReceiveBufferSize()];
				this.inputStream.read(buffer, 0, buffer.length);
                String dados = new String(buffer, "ASCII");
//            	System.out.println("dados: "  + dados);
           		this.outputStream.write(new String("LOAD").getBytes("ASCII"));
            	int bufferSize = 0;
	            do {
	            	bufferSize = inputStream.read(buffer, 0, buffer.length);
	            	dados = new String(buffer, "ASCII");
	            	System.out.println(dados);
	            	if (bufferSize > 0) {
	            		if (dados.startsWith("imei:")) {
	            			this.gravarPosicao(dados);
	            		} else {
	            			this.outputStream.write(new String("ON").getBytes("ASCII"));
	            		}
	            	}
	            } while (bufferSize > 0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	private void gravarPosicao(final String dados) {
		ThreadObjetoRastreadoService threadObjetoRastreadoService;
		threadObjetoRastreadoService = new ThreadObjetoRastreadoService(dados);
		Thread thread = new Thread(threadObjetoRastreadoService);
		thread.start();
	}
	
	public void finalizarSocket() {
		try {
			this.inputStream.close();
			this.outputStream.close();
			this.socket.close();
			this.serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		this.iniciarSocket();
	}
	
}