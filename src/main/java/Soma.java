import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Soma {
	
	
	int somaX(X raiz, List<X> lista) {
		return somaX(raiz, 0, lista);
	}
	
	private int somaX(X raiz, int acumulado, List<X> lista) {
		
		Pattern pattern = Pattern.compile(".*([1-9]+)");
		Matcher matcher = pattern.matcher(raiz.codigo);
		
		int ultimaPosicaoDiferenteZero = raiz.codigo.length();
		
		// Recuperar última posição diferente de zero no código da Natureza da Receita
		
		if (matcher.find()) {
			ultimaPosicaoDiferenteZero = matcher.end();
		}
		
		// Se possui descendentes, navegar até eles
		
		List<X> descendentes = descendentesX(raiz.codigo.substring(0, ultimaPosicaoDiferenteZero), lista);
		
		if (descendentes == null || descendentes.size() == 0) {
			
			System.out.println(raiz.codigo + "/" + raiz.valor);
			return raiz.valor;
		}
		
		while (descendentes.size() > 0) {
			
			X peek = descendentes.remove(0);
			
			acumulado = acumulado + somaX(peek, acumulado, descendentes); 
			
			return acumulado;
			//return somaX(peek, acumulado, descendentes);
		}
		
		return acumulado;
	}

	BigDecimal soma(Dado dado, List<Dado> dados) {
		return soma(dado, new BigDecimal(0), new BigDecimal(0), dados);
	}
	
	BigDecimal soma(Dado dado, BigDecimal acumulado, BigDecimal total, List<Dado> dados) {
		
		Pattern pattern = Pattern.compile(".*([1-9]+)");
		Matcher matcher = pattern.matcher(dado.codigo);
		
		int ultimaPosicaoDiferenteZero = dado.codigo.length();
		
		// Recuperar última posição diferente de zero no código da Natureza da Receita
		
		if (matcher.find()) {
			ultimaPosicaoDiferenteZero = matcher.end();
		}
		
		// Se possui descendentes, navegar até eles
		
		List<Dado> descendentes = descendentes(dado.codigo.substring(0, ultimaPosicaoDiferenteZero), dados);
		
		System.out.println(dado.codigo + " possui filhos? " + (descendentes.size() > 0 ? "Sim" : "Não"));
		
		// Se não possui descendentes, retornar valor acumulado
		
		if (descendentes == null || descendentes.size() == 0) {
			
			return dado.valor;
		}
		
		// Chamada recursiva
		
		while (descendentes.size() > 0) {
			
			Dado retorno = descendentes.remove(0);
			
			//if (descendentes.size() > 0) {
				total = total.add(soma(retorno, acumulado, total, descendentes));
				
			//} else {
				//total = acumulado.add(soma(retorno, acumulado, total, descendentes));
			//}
		}
		
		return total;
	}
	
	String recursiva(List<Dado> dados) {
		return recursiva(dados.get(0), dados);
	}
	
	String recursiva(Dado dado, List<Dado> dados) {
		
		Pattern pattern = Pattern.compile(".*([1-9]+)");
		Matcher m = pattern.matcher(dado.codigo);
		
		int index = dado.codigo.length();
		
		if (m.find()) {
			index = m.end();
		}
		
		List<Dado> descendentes = descendentes(dado.codigo.substring(0, index), dados);
		
		if (descendentes == null || descendentes.size() == 0)
			return dado.codigo;
		
		return recursiva(descendentes.remove(0), descendentes);
	}
	
	List<Dado> descendentes(String codigo, List<Dado> dados) {
		
		Pattern pattern = Pattern.compile(".*([1-9]+)");
		Matcher matcher = pattern.matcher(codigo);
		
		int ultimaPosicaoDiferenteZero = codigo.length();
		
		// Recuperar última posição diferente de zero no código da Natureza da Receita
		
		if (matcher.find()) {
			ultimaPosicaoDiferenteZero = matcher.end();
		}
		
		System.out.println(codigo);
		
		return dados.stream().filter(nr -> nr.codigo.startsWith(codigo.substring(0, matcher.end())) && Integer.valueOf(nr.codigo).compareTo(Integer.valueOf(codigo)) > 0).collect(Collectors.toList());
	}
	
	List<X> descendentesX(String codigo, List<X> dados) {
		return dados.stream().filter(nr -> nr.codigo.startsWith(codigo) && Integer.valueOf(nr.codigo).compareTo(Integer.valueOf(codigo)) > 0).collect(Collectors.toList());
	}
	
	public static void main(String[] args) {
		
		Soma teste = new Soma();
		
		List<Dado> dados = new ArrayList<Dado>();
		
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
		/*dado = teste.new Dado("12000000", new BigDecimal(2041412.25)); dados.add(dado);
		dado = teste.new Dado("12000001", new BigDecimal(2041412.25)); dados.add(dado);*/
		
		//teste.recursiva(dados);
		//System.out.println(teste.soma(dados.remove(0), dados));
//		System.out.println(teste.soma("12", dados));
		
		List<X> lista = new ArrayList<X>();
		
		lista.add(teste.new X("11000000", 70));
		lista.add(teste.new X("11100000", 70));
		lista.add(teste.new X("11120000", 70));
		lista.add(teste.new X("11120200", 15));
		lista.add(teste.new X("11120400", 30));
		
		lista.add(teste.new X("11120431", 30));
		lista.add(teste.new X("11120800", 15));
		lista.add(teste.new X("11130000", 10));
		lista.add(teste.new X("11130500", 10));
		
//		System.out.println(teste.somaX(lista.remove(0), lista));
		
//		dados.forEach(t -> System.out.println(t.codigo + ": " + t.valor));
		
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
		
		System.out.println(total);
	}
	
	class X {
		
		String codigo;
		int valor;
		
		public X(String codigo, int valor) {
			
			this.codigo = codigo;
			this.valor = valor;
		}
		
		public int getValor() {
			return valor;
		}
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
