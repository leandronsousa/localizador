package br.com.localizador.to;

import java.util.ArrayList;
import java.util.List;

import br.com.localizador.entity.ObjetoRastreado;
import br.com.localizador.entity.Posicao;
import br.com.localizador.entity.Proprietario;
import br.com.localizador.view.filtro.ObjetoRastreadoFiltro;

public class MapaTO {

	private Proprietario proprietario;
	private ObjetoRastreado objetoRastreado;
	private List<ObjetoRastreado> listaObjetoRastreado;
	
	private ObjetoRastreado objetoRastreadoAcao;
	private List<ObjetoRastreado> listaObjetoRastreadoAcao;
	private List<Posicao> listaPosicao;
	
	private ObjetoRastreadoFiltro objetoRastreadoFiltro;
	
	private String posicoes;
	
	public Proprietario getProprietario() {
		if (proprietario == null) {
			proprietario = new Proprietario();
		}
		return proprietario;
	}

	public void setProprietario(Proprietario proprietario) {
		this.proprietario = proprietario;
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

	public String getPosicoes() {
		if (posicoes == null) {
			posicoes = new String();
		}
		return posicoes;
	}

	public void setPosicoes(String posicoes) {
		this.posicoes = posicoes;
	}

	public List<ObjetoRastreado> getListaObjetoRastreado() {
		if (listaObjetoRastreado == null) {
			listaObjetoRastreado = new ArrayList<ObjetoRastreado>();
		}
		return listaObjetoRastreado;
	}

	public void setListaObjetoRastreado(List<ObjetoRastreado> listaObjetoRastreado) {
		this.listaObjetoRastreado = listaObjetoRastreado;
	}

	public ObjetoRastreado getObjetoRastreadoAcao() {
		if (objetoRastreadoAcao == null) {
			objetoRastreadoAcao = new ObjetoRastreado();
		}
		return objetoRastreadoAcao;
	}

	public void setObjetoRastreadoAcao(ObjetoRastreado objetoRastreadoAcao) {
		this.objetoRastreadoAcao = objetoRastreadoAcao;
	}

	public List<ObjetoRastreado> getListaObjetoRastreadoAcao() {
		if (listaObjetoRastreadoAcao == null) {
			listaObjetoRastreadoAcao = new ArrayList<ObjetoRastreado>();
		}
		return listaObjetoRastreadoAcao;
	}

	public void setListaObjetoRastreadoAcao(
			List<ObjetoRastreado> listaObjetoRastreadoAcao) {
		this.listaObjetoRastreadoAcao = listaObjetoRastreadoAcao;
	}

	public List<Posicao> getListaPosicao() {
		if (listaPosicao == null) {
			listaPosicao = new ArrayList<Posicao>();
		}
		return listaPosicao;
	}

	public void setListaPosicao(List<Posicao> listaPosicao) {
		this.listaPosicao = listaPosicao;
	}

	public ObjetoRastreadoFiltro getObjetoRastreadoFiltro() {
		if (objetoRastreadoFiltro == null) {
			objetoRastreadoFiltro = new ObjetoRastreadoFiltro();
		}
		return objetoRastreadoFiltro;
	}

	public void setObjetoRastreadoFiltro(ObjetoRastreadoFiltro objetoRastreadoFiltro) {
		this.objetoRastreadoFiltro = objetoRastreadoFiltro;
	}
	
}