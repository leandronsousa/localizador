package br.com.localizador.util;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

	private static final String PATTERN_CPF = "###.###.###-##";
	private static final String PATTERN_CNPJ = "##.###.###/####-##";
	
	public static String removerNaoNumericos(String string) {
		return string.replaceAll("[^0-9]", StringUtils.EMPTY);
	}
	
	public static boolean isCPFValido(String cpf) {
		cpf = StringUtil.removerNaoNumericos(cpf);
		if (!StringUtils.isBlank(cpf) && cpf.length() == 11 && StringUtils.isNumeric(cpf) 
				&& !isNumeroCPFInvalido(cpf) && isDigitoValido(cpf)) {
			return true;
		}
		return false;
	}
	
	private static boolean isDigitoValido(String cpf) {
		String num = cpf.substring(0, 9);
		Integer primDig, segDig;
		int soma = 0, peso = 10;
		for (int i = 0; i < num.length(); i++) {
			soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
		}
 
		if (soma % 11 == 0 | soma % 11 == 1) {
           primDig = new Integer(0);
		} else {
			primDig = new Integer(11 - (soma % 11));
		}
 
		soma = 0;
        peso = 11;
        for (int i = 0; i < num.length(); i++) {
        soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
        }
        soma += primDig.intValue() * 2;
        if (soma % 11 == 0 | soma % 11 == 1) {
        	segDig = new Integer(0);
        } else {
        	segDig = new Integer(11 - (soma % 11));
        }
        return (primDig.toString() + segDig.toString()).equals(cpf.substring(9,11));
	}

	private static boolean isNumeroCPFInvalido(String cpf) {
		return cpf.equals("11111111111") || cpf.equals("22222222222")
				|| cpf.equals("33333333333") || cpf.equals("44444444444")
				|| cpf.equals("55555555555") || cpf.equals("66666666666")
				|| cpf.equals("77777777777") || cpf.equals("88888888888")
				|| cpf.equals("99999999999");
	}

	public static boolean isCNPJValido(String cnpj) {
		cnpj = StringUtil.removerNaoNumericos(cnpj);
		if (!StringUtils.isBlank(cnpj) && cnpj.length() == 14 && StringUtils.isNumeric(cnpj) 
				&& isNumeroCNPJvalido(cnpj)) {
			return true;
		}
		return false;
	}

	private static boolean isNumeroCNPJvalido(String cnpj) {
		 int soma = 0;
         String cnpj_calc = cnpj.substring(0, 12);

         char chr_cnpj[] = cnpj.toCharArray();
         for(int i = 0; i < 4; i++)
              if(chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
                   soma += (chr_cnpj[i] - 48) * (6 - (i + 1));

        for(int i = 0; i < 8; i++)
             if(chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9)
                   soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));

        int dig = 11 - soma % 11;
        cnpj_calc = (new StringBuilder(String.valueOf(cnpj_calc))).append(
        		dig != 10 && dig != 11 ? Integer.toString(dig) : "0").toString();
        soma = 0;
        for(int i = 0; i < 5; i++)
             if(chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
                  soma += (chr_cnpj[i] - 48) * (7 - (i + 1));

        for(int i = 0; i < 8; i++)
             if(chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9)
                  soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));

        dig = 11 - soma % 11;
        cnpj_calc = (new StringBuilder(String.valueOf(cnpj_calc))).append(
        		dig != 10 && dig != 11 ? Integer.toString(dig) : "0").toString();

        return cnpj.equals(cnpj_calc);
	}

	public static String formatarCPF(String value) throws ParseException {
		MaskFormatter mask = new MaskFormatter(PATTERN_CPF);
        mask.setValueContainsLiteralCharacters(false);
        return mask.valueToString(value);
	}
	
	public static String formatarCNPJ(String value) throws ParseException {
		MaskFormatter mask = new MaskFormatter(PATTERN_CNPJ);
        mask.setValueContainsLiteralCharacters(false);
        return mask.valueToString(value);
	}
	
}