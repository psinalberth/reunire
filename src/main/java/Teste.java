import java.util.List;
import java.util.Map;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;

public class Teste {

	public static void main(String[] args) {
		
		DemonstrativoDao dao = new DemonstrativoDao() {

			@Override
			public List recuperaDados(Map params) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getNomeRelatorio() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		System.out.println(dao.toPessoa("1598547000101"));
		
		/*(case " + 
				"when cam19.cpf_cnpj_credor ~ '000[[:digit:]]{3}$' then regexp_replace(lpad(cam19.cpf_cnpj_credor, 14, '0'), '([[:digit:]]{2})([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{4})([[:digit:]]{2})', '\\1.\\2.\\3/\\4-\\5') " +
				"when cam19.cpf_cnpj_credor ~ '^[0-9]*$' and length(cam19.cpf_cnpj_credor) > 1 then regexp_replace(lpad(cpf_cnpj_credor, 11, '0'), '([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{3})([[:digit:]]{2})', '\\1.\\2.\\3-\\4') " +
				"else cam19.cpf_cnpj_credor " +
			"end) credor,
*/		
		
	}
}