import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class XYZ {
	
	List<Dado> descendentes(String codigo, List<Dado> dados) {
		
		Pattern pattern = Pattern.compile(".*([1-9]+)");
		Matcher matcher = pattern.matcher(codigo);
		
		int ultimaPosicaoDiferenteZero = codigo.length();
		
		// Recuperar última posição diferente de zero no código da Natureza da Receita
		
		if (matcher.find()) {
			ultimaPosicaoDiferenteZero = matcher.end();
		}
		
//		System.out.println(codigo);
		
//		List<Dado> lista = dados.stream().filter(nr -> nr.codigo.startsWith(codigo.substring(0, matcher.end())))
//				.collect(Collectors.toList()); 
		
		List<Dado> lista = dados.stream().filter(nr -> nr.codigo.startsWith(codigo.substring(0, matcher.end())) && Integer.valueOf(nr.codigo).compareTo(Integer.valueOf(codigo)) > 0)
				.collect(Collectors.toList()); 
		
//		System.out.println(lista.size());
		
		return lista;
	}
	
	public static void main(String[] args) {
		
		List<Dado> dados = new ArrayList<Dado>();
		
		XYZ teste = new XYZ();
		
		Dado dado = teste.new Dado("11000000", new BigDecimal(21791868.9800000004)); dados.add(dado);
		dado = teste.new Dado("11100000", new BigDecimal(19919070.5799999982)); dados.add(dado);
		dado = teste.new Dado("11120000", new BigDecimal(8503711.03999999911)); dados.add(dado);
		dado = teste.new Dado("11120200", new BigDecimal(2257016.56000000006)); dados.add(dado);
		dado = teste.new Dado("11120400", new BigDecimal(3379312.64999999991)); dados.add(dado);
		dado = teste.new Dado("11120431", new BigDecimal(3379312.64999999991)); dados.add(dado);
		dado = teste.new Dado("11120800", new BigDecimal(2867381.83000000007)); dados.add(dado);
		dado = teste.new Dado("11130000", new BigDecimal(11415359.5399999991)); dados.add(dado);
		dado = teste.new Dado("11130500", new BigDecimal(11415359.5399999991)); dados.add(dado);
		dado = teste.new Dado("11200000", new BigDecimal(1697108.23999999999)); dados.add(dado);
		dado = teste.new Dado("11210000", new BigDecimal(1545436.76000000001)); dados.add(dado);
		dado = teste.new Dado("11211000", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("11211000", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("11211700", new BigDecimal(20593.0800000000017)); dados.add(dado);
		dado = teste.new Dado("11212100", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("11212100", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("11212100", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("11212100", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("11212500", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("11212500", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("11212500", new BigDecimal(458022.409999999974)); dados.add(dado);
		dado = teste.new Dado("11212500", new BigDecimal(458022.409999999974)); dados.add(dado);
		dado = teste.new Dado("11212600", new BigDecimal(13882.9799999999996)); dados.add(dado);
		dado = teste.new Dado("11212800", new BigDecimal(5206.10999999999967)); dados.add(dado);
		dado = teste.new Dado("11212900", new BigDecimal(18973.4000000000015)); dados.add(dado);
		dado = teste.new Dado("11213000", new BigDecimal(907282.770000000019)); dados.add(dado);
		dado = teste.new Dado("11213100", new BigDecimal(9718.07999999999993)); dados.add(dado);
		dado = teste.new Dado("11219900", new BigDecimal(111757.929999999993)); dados.add(dado);
		dado = teste.new Dado("11220000", new BigDecimal(151671.48000000001)); dados.add(dado);
		dado = teste.new Dado("11220400", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("11220400", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("11222800", new BigDecimal(9718.07999999999993)); dados.add(dado);
		dado = teste.new Dado("11229000", new BigDecimal(115.689999999999998)); dados.add(dado);
		dado = teste.new Dado("11229900", new BigDecimal(141837.709999999992)); dados.add(dado);
		dado = teste.new Dado("11300000", new BigDecimal(175690.160000000003)); dados.add(dado);
		dado = teste.new Dado("11300100", new BigDecimal(115.689999999999998)); dados.add(dado);
		dado = teste.new Dado("11300200", new BigDecimal(74389.6000000000058)); dados.add(dado);
		dado = teste.new Dado("11300300", new BigDecimal(29617)); dados.add(dado);
		dado = teste.new Dado("11300400", new BigDecimal(71452.179999999993)); dados.add(dado);
		dado = teste.new Dado("11309900", new BigDecimal(115.689999999999998)); dados.add(dado);
		
		dados.removeIf(new Predicate<Dado>() {

			@Override
			public boolean test(Dado dado) {
				
				Pattern pattern = Pattern.compile(".*([1-9]+)");
				Matcher matcher = pattern.matcher(dado.codigo);
				
				int ultimaPosicaoDiferenteZero = dado.codigo.length();
				
				// Recuperar última posição diferente de zero no código da Natureza da Receita
				
				if (matcher.find()) {
					ultimaPosicaoDiferenteZero = matcher.end();
				}
				
				return teste.descendentes(dado.codigo, dados).size() > 0;
			}
		});
		
		BigDecimal total = dados.stream().filter(item -> item.getValor() != null).map(Dado::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
		System.out.println("Total = " + total);
	}
	
	class Dado {
		
		String codigo;
		BigDecimal valor;
		
		public Dado(String codigo, BigDecimal valor) {
			
			this.codigo = codigo;
			this.valor = valor;
		}
		
		public BigDecimal getValor() {
			return valor;
		}
	}
}
