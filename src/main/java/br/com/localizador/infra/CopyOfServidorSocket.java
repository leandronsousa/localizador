package br.com.localizador.infra;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CopyOfServidorSocket {
	
	
	
	public void iniciaSocket() {
		System.out.println("Socket sendo inicializado...");
	}
	
	public void finalizaSocket() {
		System.out.println("Socket sendo finalizados!");
	}
	
	private void criaSocket() {
	
		InputStream entrada = null;
		OutputStream saida = null;
		Socket socket = null;
		ServerSocket serverSocket = null;
		
		PrintWriter out = null;
		BufferedReader in = null;
		
		DataInputStream inbound = null;
		DataOutputStream outbound = null; 
		
		try {
			serverSocket = new ServerSocket(7000);
			while (true) {
				
				socket = serverSocket.accept();
				
				out = new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				inbound = new DataInputStream(socket.getInputStream()); 
				outbound = new DataOutputStream(socket.getOutputStream());
				
//				String e = in.readLine().toString();
////				String s = null;
//				System.out.println("a");
//				System.out.println(e);
				
//				if (e.equals("##,imei:863070010729159,A;")) {
//				} else if (e.equals("863070010729159;")) {
//				}
				
				entrada = socket.getInputStream();
				saida = socket.getOutputStream();
				byte[] buffer = new byte[socket.getReceiveBufferSize()];
	            StringBuffer sb = new StringBuffer();
	            
	            entrada.read(buffer, 0, buffer.length);
                String dados = new String(buffer, "ASCII");
            	System.out.println("dados: "  + dados);
//            	if (dados.indexOf(";") > 0) dados = dados.substring(0, dados.indexOf(";") + 1);
            	System.out.println("dados2: "  + dados);
            	
            	if (dados.indexOf(";") <= 0) {
            		saida.write(new String("LOAD").getBytes("ASCII"));
            		entrada.read(buffer, 0, buffer.length);
	            	System.out.println(new String(buffer, "ASCII"));
            	} else if (dados.indexOf(";") > 0) {
            		saida.write(new String("ON").getBytes("ASCII"));
            		saida.write(new String("##,imei:863070010729159,B;").getBytes("ASCII"));
            	}
            	entrada.read(buffer, 0, buffer.length);
            	System.out.println(new String(buffer, "ASCII"));
	            
	            while (entrada.read(buffer, 0, buffer.length) > 0) {
//	            	entrada.read(buffer, 0, buffer.length);
	            	System.out.println(new String(buffer, "ASCII"));
	            }
	            	entrada.close();
	            	saida.close();
	            	socket.close();
	            	
//	            	sb.append(new String(buffer));
//	                System.out.println(sb.toString());
//	                if (sb.toString().startsWith("#")) {
//	                	out.println("LOAD");
//	                	saida.write(new String("LOAD").getBytes());
////	                	saida.write("LOAD".getBytes());
//	                } 
//	                if (sb.toString().indexOf(";") > 1){
//	                	out.println("ON");
//	                	out.println("##,imei:863070010729159,B;");
//	                	saida.write(new String("ON").getBytes());
//	                	saida.write(new String("##,imei:863070010729159,B;").getBytes());
////	                	saida.write("ON".getBytes());
//	                }
	            }
//			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				in.close();
				outbound.close();
				inbound.close();
				socket.close();
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("terminou");
	}

}
