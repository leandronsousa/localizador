package br.com.localizador.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.math.NumberUtils;

import br.com.localizador.enums.PontosCardeaisEnum;


public class PosicaoUtil {
	
	private static final BigDecimal _100 = new BigDecimal(100);
	private static final BigDecimal _60 = new BigDecimal(60);
	private static final int _0 = NumberUtils.INTEGER_ZERO.intValue();
	private static final int _2 = 2;
	private static final int _6 = 6;
	private static final BigDecimal MPH_KMH = new BigDecimal(1.609344); 
	
	public static BigDecimal converterLatitude(BigDecimal latitude, Character hemisferio) {
		BigDecimal posicaoDivCem = latitude.divide(_100, _0, RoundingMode.DOWN);
		BigDecimal resultado = posicaoDivCem.add(latitude.subtract(
				posicaoDivCem.multiply(_100)).divide(_60, RoundingMode.UP));
		if (hemisferio.charValue() == PontosCardeaisEnum.SUL.getAbreviacao().charValue()) {
			resultado = resultado.negate();
		}
		return resultado.setScale(_6, RoundingMode.HALF_EVEN);
	}
	
	public static BigDecimal converterLongitude(BigDecimal longitude, Character hemisferio) {
		BigDecimal posicaoDivCem = longitude.divide(_100, _0, RoundingMode.DOWN);
		BigDecimal resultado = posicaoDivCem.add(longitude.subtract(
				posicaoDivCem.multiply(_100)).divide(_60, RoundingMode.HALF_EVEN));
		if (hemisferio.charValue() == PontosCardeaisEnum.OESTE.getAbreviacao().charValue()) {
			resultado = resultado.negate();
		}
		return resultado.setScale(_6, RoundingMode.HALF_EVEN);
	}
	
	/**
	 * Converte velocidade de mp/h para km/h.
	 * @param velocidade
	 * @return
	 */
	public static BigDecimal converterVelocidade(BigDecimal velocidade) {
		return velocidade.multiply(MPH_KMH).setScale(_2, RoundingMode.HALF_EVEN);
	}
	
}