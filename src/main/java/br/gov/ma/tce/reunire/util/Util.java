package br.gov.ma.tce.reunire.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Util {
	
	public static BigDecimal soma(String regex, List<Object[]> dados, int posicao) {
		
		String codigoNivelDois = regex + "000000";
		
		Optional<Object[]> objetoNivelDois = dados.stream().filter(obj -> String.valueOf(obj[0]).matches(codigoNivelDois)).findAny();
		
		if (objetoNivelDois.isPresent())
			return new BigDecimal(String.valueOf(objetoNivelDois.get()[posicao]));
		
		List<Object[]> filtro = dados.stream().filter(item -> String.valueOf(item[0]).matches(regex + ".*")).collect(Collectors.toList());
		
		filtro.removeIf(new Predicate<Object[]>() {

			@Override
			public boolean test(Object[] obj) {
				
				Pattern pattern = Pattern.compile(".*([1-9]+)");
				String codigo = String.valueOf(obj[0]);
				Matcher matcher = pattern.matcher(codigo);
				
				matcher.find();
				
				return (filtro.stream().filter(item -> String.valueOf(item[0]).startsWith(codigo.substring(0, matcher.end())) && Integer.valueOf(String.valueOf(item[0])).compareTo(Integer.valueOf(String.valueOf(obj[0]))) > 0).collect(Collectors.toList())).size() > 0;
			}
		});
		
		BigDecimal total = BigDecimal.ZERO;
		
		for (Object [] obj : filtro) {
			total = total.add(new BigDecimal(String.valueOf(obj[posicao])));
		}
		
		return total;
	}
	
	public static Date toDate(Object obj) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(((Timestamp) obj).getTime());
		
		return calendar.getTime();
	}
}