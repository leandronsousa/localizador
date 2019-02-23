package br.com.localizador.to;

import java.util.ArrayList;

import br.com.localizador.entity.ObjetoRastreado;
import br.com.localizador.entity.PerfilUsuario;
import br.com.localizador.entity.Proprietario;
import br.com.localizador.entity.Usuario;

public class ProprietarioTO {

	private Proprietario proprietario;
	private Proprietario proprietarioPesquisa;
	
	private ObjetoRastreado objetoRastreado;
	private Usuario usuario;
	
	public Proprietario getProprietario() {
		if (proprietario == null) {
			proprietario = new Proprietario();
			proprietario.setListaObjetoRastreado(new ArrayList<ObjetoRastreado>());
			proprietario.setListaUsuario(new ArrayList<Usuario>());
		}
		return proprietario;
	}
	
	public void setProprietario(Proprietario proprietario) {
		this.proprietario = proprietario;
	}
	
	public Proprietario getProprietarioPesquisa() {
		if (proprietarioPesquisa == null) {
			proprietarioPesquisa = new Proprietario();
		}
		return proprietarioPesquisa;
	}

	public void setProprietarioPesquisa(Proprietario proprietarioPesquisa) {
		this.proprietarioPesquisa = proprietarioPesquisa;
	}

	public ObjetoRastreado getObjetoRastreado() {
		if (objetoRastreado == null) {
			objetoRastreado = new ObjetoRastreado();
		}
		return objetoRastreado;
	}

	public void setObjetoRastreado(ObjetoRastreado objetoRastreado) {
		this.objetoRastreado = objetoRastreado;
	}

	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
			usuario.setPerfilUsuario(new PerfilUsuario());
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}