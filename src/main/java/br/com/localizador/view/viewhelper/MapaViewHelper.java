package br.com.localizador.view.viewhelper;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.security.annotations.LoggedIn;

import br.com.localizador.entity.ObjetoRastreado;
import br.com.localizador.security.CustomIdentity;
import br.com.localizador.service.ObjetoRastreadoService;
import br.com.localizador.service.PosicaoService;
import br.com.localizador.to.MapaTO;
import br.com.localizador.view.NavegacaoView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Named
@ConversationScoped
public class MapaViewHelper extends NavegacaoView {

	private static final long serialVersionUID = 1362359777704249521L;
	
	private @Inject Conversation conversation;
//	private @Inject ProprietarioService proprietarioService;
	private @Inject ObjetoRastreadoService objetoRastreadoService;
	private @Inject PosicaoService posicaoService;
	private @Inject CustomIdentity identity;
	
	private boolean seguir = true;
	
	private MapaTO mapaTO;

	@PostConstruct
	public void init() throws Exception {
		if(this.conversation.isTransient()){
			this.conversation.begin();
			inicializarPropriedades();
		}
	}

	private @LoggedIn void inicializarPropriedades() throws Exception {
		getMapaTO().setProprietario(this.identity.getUsuario().getProprietario());
		getMapaTO().setListaObjetoRastreado(
				this.objetoRastreadoService.recuperarObjetosRastreadosProprietario(
						getMapaTO().getProprietario()));
		if (!getMapaTO().getListaObjetoRastreado().isEmpty()) {
			getMapaTO().setObjetoRastreado(getMapaTO().getListaObjetoRastreado().get(0));
			carregarPosicoes();
		}
	}

	private void converterPosicoesEmJson(ObjetoRastreado objetoRastreado) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		getMapaTO().setPosicoes(gson.toJson(objetoRastreado));
	}

	public void carregarPosicoes() throws Exception {
		carregarPosicoesObjetoProprietario();
		removerPosicoesDuplicadas(getMapaTO().getObjetoRastreadoAcao());
		converterPosicoesEmJson(getMapaTO().getObjetoRastreadoAcao());
	}
	
	private void removerPosicoesDuplicadas(ObjetoRastreado objetoRastreado) throws Exception {
		int cont = 0;
		for (int i = 0; cont < objetoRastreado.getListaPosicao().size(); ) {
			if (i > 0) {
				if (objetoRastreado.getListaPosicao().get(cont-1).equals(
						objetoRastreado.getListaPosicao().get(cont))) {
					objetoRastreado.getListaPosicao().remove(
							objetoRastreado.getListaPosicao().get(cont-1));
				} else {
					cont++;
				}
			} else {
				cont++;
				i++;
			}
		}
	}

	private void carregarPosicoesObjetoProprietario() throws Exception {
		getMapaTO().setObjetoRastreadoAcao(new ObjetoRastreado());
		getMapaTO().getObjetoRastreadoAcao().setNome(
				getMapaTO().getObjetoRastreado().getNome());
		getMapaTO().getObjetoRastreadoAcao().setListaPosicao(
				posicaoService.recuperarPosicoesObjetoRastreado(
						getMapaTO().getObjetoRastreado(),
						getMapaTO().getObjetoRastreadoFiltro()));
		getMapaTO().getObjetoRastreado().getListaPosicao().addAll(
				getMapaTO().getObjetoRastreadoAcao().getListaPosicao());
	}
	
	public void selecionarObjetoRastreado() throws Exception {
		this.objetoRastreadoService.recuperar(getMapaTO().getObjetoRastreado());
		getMapaTO().setObjetoRastreadoFiltro(null);
		this.seguir = true;
		carregarPosicoes();
	}
	
	public void atualizar() throws Exception {
		if (this.seguir) {
			getMapaTO().setObjetoRastreadoFiltro(null);
			carregarPosicoes();
		}
	}
	
	public void filtrar() throws Exception {
		this.seguir = false;
		carregarPosicoes();
	}
	
	public Conversation getConversation() {
		return conversation;
	}
	
	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public MapaTO getMapaTO() {
		if (mapaTO == null) {
			mapaTO = new MapaTO();
		}
		return mapaTO;
	}

	public void setMapaTO(MapaTO mapaTO) {
		this.mapaTO = mapaTO;
	}

	public boolean isSeguir() {
		return seguir;
	}

	public void setSeguir(boolean seguir) {
		this.seguir = seguir;
	}

	public Date getDataAtual() {
		Calendar dataAtual = Calendar.getInstance();
		dataAtual.set(Calendar.HOUR_OF_DAY, 23);
		dataAtual.set(Calendar.MINUTE, 59);
		dataAtual.set(Calendar.SECOND, 59);
		return dataAtual.getTime();
	}

}