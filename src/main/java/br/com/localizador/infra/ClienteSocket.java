package br.com.localizador.infra;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClienteSocket {

	public static void main(String[] args) {
		
		Socket socket = null;
		PrintStream printStream = null;
		
		try {
			socket = new Socket("177.17.174.248", 7000);
			printStream = new PrintStream(socket.getOutputStream());
			printStream.println("Enviando para o servidor!");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("terminou cliente");
	}
	
}
