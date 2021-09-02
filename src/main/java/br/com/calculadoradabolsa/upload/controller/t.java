package br.com.calculadoradabolsa.upload.controller;

import br.com.calculadoradabolsa.notanegociacao.enums.EnumTipoAcao;
import br.com.calculadoradabolsa.utils.Utils;

public class t {

	public static void main(String[] args) {
		String a = "1-BOVESPA C VISTA FII CSHGLOG HGLG11 CI 2D# 1 150,31 150,31 D";
		String b = "1-BOVESPA C FRACIONARIO MAGAZ LUIZA          ON NM 2D# 6 42,45 254,70 D";
		String d = "1-BOVESPA V FRACIONARIO BRADESCO          PN N1 D2# 36 34,98 1.259,28 C";

		EnumTipoAcao enumTipoAcao = EnumTipoAcao.FIL;
		String tipoAcao = " ON ";
		String tipoMercado = " VISTA ";

		String regex2 = "(?ims).*,\\d{2}$";
		String regex = "(?ims)^\\d{1,}.*";

		String empresa;
		String observacao;

		if (enumTipoAcao.equals(EnumTipoAcao.FIL)) {
			empresa = a.substring(a.indexOf(tipoMercado) + tipoMercado.length());
			empresa.substring(0, empresa.indexOf(Utils.getStringByRegex(empresa,
					"\\s\\d{1,}\\s\\d{1,}\\s|\\s\\w{1,}\\s\\d{1,}\\s|\\s\\w{1,}#\\s|\\s#\\s")));
			System.out.println(empresa);
		} else {
			empresa = a.substring(a.indexOf(tipoMercado) + tipoMercado.length());
			System.out.println(empresa.substring(0, empresa.indexOf(tipoAcao)).trim());
			System.out.println(empresa);
		}
		observacao = Utils.getStringByRegex(empresa,
				"\\s\\d{1,}\\s\\d{1,}\\s|\\s\\w{1,}\\s\\d{1,}\\s|\\s\\w{1,}#\\s|\\s#\\s");
		if (observacao != null) {
			observacao = observacao.trim();
			if (observacao.contains(" ")) {
				observacao = observacao.substring(0, observacao.indexOf(" "));
			}
		}

		System.out.println(observacao);
		tipoMercado = " FRACIONARIO ";
		enumTipoAcao = EnumTipoAcao.ON;
		a = b;
		if (enumTipoAcao.equals(EnumTipoAcao.FIL)) {
			empresa = a.substring(a.indexOf(tipoMercado) + tipoMercado.length());
			System.out.println(empresa.substring(0, empresa.indexOf(Utils.getStringByRegex(empresa,
					"\\s\\d{1,}\\s\\d{1,}\\s|\\s\\w{1,}\\s\\d{1,}\\s|\\s\\w{1,}#\\s|\\s#\\s"))));
			System.out.println(empresa);
		} else {
			empresa = a.substring(a.indexOf(tipoMercado) + tipoMercado.length());
			empresa.substring(0, empresa.indexOf(tipoAcao)).trim();
			System.out.println(empresa);
		}

		observacao = Utils.getStringByRegex(empresa,
				"\\s\\d{1,}\\s\\d{1,}\\s|\\s\\w{1,}\\s\\d{1,}\\s|\\s\\w{1,}#\\s|\\s#\\s");
		System.out.println(observacao);

		tipoAcao = " PN ";
		enumTipoAcao = EnumTipoAcao.PN;
		a = d;

		if (enumTipoAcao.equals(EnumTipoAcao.FIL)) {
			empresa = a.substring(a.indexOf(tipoMercado) + tipoMercado.length());
			System.out.println(empresa.substring(0, empresa.indexOf(Utils.getStringByRegex(empresa,
					"\\s\\d{1,}\\s\\d{1,}\\s|\\s\\w{1,}\\s\\d{1,}\\s|\\s\\w{1,}#\\s|\\s#\\s"))));
			System.out.println(empresa);
		} else {
			empresa = a.substring(a.indexOf(tipoMercado) + tipoMercado.length());
			empresa.substring(0, empresa.indexOf(tipoAcao)).trim();
			System.out.println(empresa);
		}

		observacao = Utils.getStringByRegex(empresa,
				"\\s\\d{1,}\\s\\d{1,}\\s|\\s\\w{1,}\\s\\d{1,}\\s|\\s\\w{1,}#\\s|\\s#\\s");
		System.out.println(observacao);

	}

}
