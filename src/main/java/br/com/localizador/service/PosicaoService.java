package br.com.localizador.service;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.math.NumberUtils;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.LatLng;

import br.com.localizador.entity.ObjetoRastreado;
import br.com.localizador.entity.Posicao;
import br.com.localizador.entitybean.PosicaoBean;
import br.com.localizador.util.DataHoraUtil;
import br.com.localizador.util.PosicaoUtil;
import br.com.localizador.view.filtro.ObjetoRastreadoFiltro;

@Named
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@TransactionManagement(TransactionManagementType.CONTAINER)
public @Stateless class PosicaoService {

	private @Inject PosicaoBean posicaoBean;
	private @Inject ObjetoRastreadoService objetoRastreadoService;
	
	public void salvar(String dados) throws Exception {
		Posicao posicao = this.converterDadosRecebidos(dados);
		if (isPosicaoValida(posicao)) {
			this.converterLatLng(posicao);
			this.capturarEndereco(posicao);
			this.posicaoBean.incluir(posicao);
		}
	}

	private void converterLatLng(Posicao posicao) {
		posicao.setLatitude(PosicaoUtil.converterLatitude(
				posicao.getLatitude(), posicao.getHemisferioLat()));
		posicao.setLongitude(PosicaoUtil.converterLongitude(
				posicao.getLongitude(), posicao.getHemisferioLtn()));
	}

	private void capturarEndereco(Posicao posicao) {
		capturarGeocode(posicao);
	}

	private void capturarGeocode(Posicao posicao) {
		Geocoder geocoder = new Geocoder();
		LatLng location = new LatLng(posicao.getLatitude(), posicao.getLongitude()); 
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder()
				.setLocation(location).setLanguage("pt-br")
				.setRegion("pt-br").getGeocoderRequest();
		GeocodeResponse geocodeResponse = geocoder.geocode(geocoderRequest);
		if (!geocodeResponse.getResults().isEmpty()) {
			posicao.setEndereco(geocodeResponse.getResults().get(0).getFormattedAddress());
		}
	}

	private boolean isPosicaoValida(Posicao posicao) {
		return posicao.getObjetoRastreado() != null
				&& posicao.getLatitude() != null
				&& posicao.getLongitude() != null
				&& posicao.getHemisferioLat() != null
				&& posicao.getHemisferioLtn() != null;
	}

	private Posicao converterDadosRecebidos(String dados) throws Exception {
		Posicao posicao = new Posicao();
		String [] split = dados.split(",");
		int posicaoArray = 0;
		for (String str : split) {
			switch (posicaoArray) {
				case 0:
					definirObjetoRastreado(posicao, str);
					break;
				case 1:
					break;
				case 2:
					definirDataHoraPosicao(posicao, str);
					break;
				case 3:
					posicao.setTelefone(NumberUtils.isNumber(str) ? NumberUtils.createBigInteger(str) : null);
					break;
				case 4:
					break;
				case 5:
					break;
				case 6:
					break;
				case 7:
					posicao.setLatitude(NumberUtils.isNumber(str) ? NumberUtils.createBigDecimal(str) : null);
					break;
				case 8:
					posicao.setHemisferioLat(str.isEmpty() ? null : str.charAt(0));
					break;
				case 9:
					posicao.setLongitude(NumberUtils.isNumber(str) ? NumberUtils.createBigDecimal(str) : null);
					break;
				case 10:
					posicao.setHemisferioLtn(str.isEmpty() ? null : str.charAt(0));
					break;
				case 11:
					posicao.setVelocidade(calcularVelocidade(str));
					break;
				case 12:
					break;
				case 13:
					break;
			} posicaoArray++;
		}
		return posicao;
	}

	private BigDecimal calcularVelocidade(String str) {
		return str.isEmpty() ? null : PosicaoUtil.converterVelocidade(
				NumberUtils.createBigDecimal(str));
	} 

	private void definirDataHoraPosicao(Posicao posicao, String str) throws Exception {
		posicao.setDataHora(DataHoraUtil.converteStringToDate(str));
	}

	private void definirObjetoRastreado(Posicao posicao, String str) throws Exception {
		if (posicao.getObjetoRastreado() == null) {
			posicao.setObjetoRastreado(new ObjetoRastreado());
		}
		posicao.getObjetoRastreado().setImei(
				str.substring(str.indexOf(":")+1, str.length()));
		posicao.setObjetoRastreado(this.objetoRastreadoService.recuperar(
				posicao.getObjetoRastreado()));
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Posicao> recuperarPosicoesObjetoRastreado(
			ObjetoRastreado objetoRastreado, 
			ObjetoRastreadoFiltro objetoRastreadoFiltro) throws Exception {
		return this.posicaoBean.recuperarPosicoesObjetoRastreado(
				objetoRastreado, objetoRastreadoFiltro);
	}
	
}
