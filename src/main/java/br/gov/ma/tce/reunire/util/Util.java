package br.gov.ma.tce.reunire.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Util {
	
	public static BigDecimal soma(int modulo, String regex, List<Object[]> dados, int posicao) {
		
		// Código utilizado para pesquisar no segundo nível (origem/grupo)
		
		String codigoNivelDois = regex + "000000";
		
		// Query sem resultados
		
		if (dados == null || dados.size() == 0)
			return BigDecimal.ZERO;
		
		Optional<Object[]> objetoNivelDois = dados.stream().filter(obj -> String.valueOf(obj[2]).matches(codigoNivelDois) && 
				Integer.valueOf(String.valueOf(obj[0])).intValue() == modulo).findAny();
		
		// Se possui registro no nível 2
		
		if (objetoNivelDois.isPresent())
			return toBigDecimal(objetoNivelDois.get()[posicao]);
		
		// Procurar todos os registros que se aplicam na regex
		
		List<Object[]> filtro = dados.stream().filter(item -> String.valueOf(item[2]).matches(regex + ".*")).collect(Collectors.toList());
		
		// Remove todas as "subárvores", restando somente as folhas da árvore
		
		filtro.removeIf(new Predicate<Object[]>() {

			@Override
			public boolean test(Object[] obj) {
				
				Pattern pattern = Pattern.compile(".*([1-9]+)");
				String codigo = String.valueOf(obj[2]);
				Matcher matcher = pattern.matcher(codigo);
				
				matcher.find();
				
				return (filtro.stream().filter(item -> String.valueOf(item[2]).startsWith(codigo.substring(0, matcher.end())) && Integer.valueOf(String.valueOf(item[0])).compareTo(Integer.valueOf(String.valueOf(obj[0]))) > 0).collect(Collectors.toList())).size() > 0;
			}
		});
		
		BigDecimal total = BigDecimal.ZERO;
		
		for (Object [] obj : filtro) {
			total = total.add(toBigDecimal(obj[posicao]));
		}
		
		return total;
	}
	
	public static BigDecimal toBigDecimal(Object obj) {
		
		if (obj == null)
			return null;
		
		if (obj instanceof Double) {
			
			Double valor = (Double) obj;
			DecimalFormat format = new DecimalFormat("#,##0.##", new DecimalFormatSymbols(new Locale("pt", "BR")));
			
			String formatted = format.format(valor);
			formatted = formatted.replace(".", "");
			formatted = formatted.replace(",", ".");
			formatted = formatted.trim();
			
			return new BigDecimal(formatted);
		}
		
		return new BigDecimal(String.valueOf(obj));
	}
	
	public static Date toDate(Object obj) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(((Timestamp) obj).getTime());
		
		return calendar.getTime();
	}
}