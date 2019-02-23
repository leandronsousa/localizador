package br.com.localizador.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataHoraUtil {
	
	private static final SimpleDateFormat FORMAT_PT_BR = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public static Date converteStringToDate(String string) {
		Calendar calendar = Calendar.getInstance();
//		int ano = Integer.parseInt(string.substring(0, 2));
		int mes = Integer.parseInt(string.substring(2, 4));
		int dia = Integer.parseInt(string.substring(4, 6));
		int hora = Integer.parseInt(string.substring(6, 8));
		int minuto = Integer.parseInt(string.substring(8, 10));
		calendar.set(calendar.get(Calendar.YEAR), mes-1, dia, hora, minuto);
		return calendar.getTime();
	}
	
	public static String converterDataToString(Date data) {
		return (data != null ? FORMAT_PT_BR.format(data) : null);
	}
	
	public static Date adicionarMinutos(Date data, int minutos) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		calendar.add(Calendar.MINUTE, minutos);
		return calendar.getTime();
	}
	
}