import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Teste {
	
	boolean possuiDescendentes(String codigo, List<Dado> dados) {
		return dados.stream().filter(nr -> nr.codigo.startsWith(codigo) && Integer.valueOf(nr.codigo).compareTo(Integer.valueOf(codigo)) > 0).collect(Collectors.counting()) > 0;
	}
	
	List<Dado> descendentes(String codigo, List<Dado> dados) {
		return dados.stream().filter(nr -> nr.codigo.startsWith(codigo) && Integer.valueOf(nr.codigo).compareTo(Integer.valueOf(codigo)) > 0).collect(Collectors.toList());
	}
	
	BigDecimal somaRecursiva(String codigo, List<Dado> dados) {
		
		List<Dado> descendentes = descendentes(codigo, dados);
		
		if (descendentes == null || descendentes.size() == 0)
			return new BigDecimal(0);
		
		return somaRecursiva(codigo, descendentes);
	}
	
	// soma
	
	int xx(int numero) {
		
		if (numero == 0)
			return 0;
		
		return numero + xx(numero - 1);
	}
	
	// chars
	
	String xy(String codigo) {
		
		if (codigo.length() == 0)
			return "";
		
		return xy(codigo, 0, 1);
	}
	
	String xy(String codigo, int i, int j) {
		
		if (i == codigo.length())
			return "";
		
		if (codigo.length() - j < 0)
			return "";
		
		return codigo.substring(i, j) + "/" + xy(codigo, i+1, j+1);
	}
	
	BigDecimal recursiva(Dado dado, List<Dado> dados) {
		return recursiva(dado, dados, 1);
	}
	
	BigDecimal recursiva(Dado dado, List<Dado> dados, int index) {
		
		if (dado == null) {
			dado = dados.remove(0);
		}
		
		Pattern pattern = Pattern.compile(".*([1-9]+)");
		Matcher m = pattern.matcher(dado.codigo);
		
		int idd = index;
		
		if (m.find()) {
			idd = m.end();
		}
		
		List<Dado> descendentes = descendentes(dado.codigo.substring(0, idd), dados);
		
		if (descendentes == null || descendentes.size() == 0)
			return dado.valor;
		
		return new BigDecimal(0).add(recursiva(descendentes.remove(0), descendentes, 1));
	}
	
	public static void main(String[] args) {
		
		Teste teste = new Teste();
		
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
		dado = teste.new Dado("12000000", new BigDecimal(2041412.25)); dados.add(dado);
		
		/*dado = teste.new Dado("12300000", new BigDecimal(2041412.25)); dados.add(dado);
		dado = teste.new Dado("13000000", new BigDecimal(2009265.25)); dados.add(dado);
		dado = teste.new Dado("13100000", new BigDecimal(894621.069999999949)); dados.add(dado);
		dado = teste.new Dado("13110000", new BigDecimal(15452.8999999999996)); dados.add(dado);
		dado = teste.new Dado("13119900", new BigDecimal(15452.8999999999996)); dados.add(dado);
		dado = teste.new Dado("13120000", new BigDecimal(22328.4500000000007)); dados.add(dado);
		dado = teste.new Dado("13130000", new BigDecimal(827309.479999999981)); dados.add(dado);
		dado = teste.new Dado("13140000", new BigDecimal(14230.0499999999993)); dados.add(dado);
		dado = teste.new Dado("13190000", new BigDecimal(15300.1900000000005)); dados.add(dado);
		dado = teste.new Dado("13200000", new BigDecimal(767307.260000000009)); dados.add(dado);
		dado = teste.new Dado("13220000", new BigDecimal(15155.5799999999999)); dados.add(dado);
		dado = teste.new Dado("13250000", new BigDecimal(752151.680000000051)); dados.add(dado);
		dado = teste.new Dado("13250100", new BigDecimal(752151.680000000051)); dados.add(dado);
		dado = teste.new Dado("13250101", new BigDecimal(578.460000000000036)); dados.add(dado);
		dado = teste.new Dado("13250101", new BigDecimal(277890.830000000016)); dados.add(dado);
		dado = teste.new Dado("13250101", new BigDecimal(278469.289999999979)); dados.add(dado);
		dado = teste.new Dado("13250102", new BigDecimal(256372.239999999991)); dados.add(dado);
		dado = teste.new Dado("13250103", new BigDecimal(49747.3099999999977)); dados.add(dado);
		dado = teste.new Dado("13250105", new BigDecimal(694.139999999999986)); dados.add(dado);
		dado = teste.new Dado("13250105", new BigDecimal(27997.3300000000017)); dados.add(dado);
		dado = teste.new Dado("13250105", new BigDecimal(3933.51000000000022)); dados.add(dado);
		dado = teste.new Dado("13250105", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("13250105", new BigDecimal(76587.7299999999959)); dados.add(dado);
		dado = teste.new Dado("13250105", new BigDecimal(34938.8199999999997)); dados.add(dado);
		dado = teste.new Dado("13250105", new BigDecimal(115.689999999999998)); dados.add(dado);
		dado = teste.new Dado("13250105", new BigDecimal(4974.73999999999978)); dados.add(dado);
		dado = teste.new Dado("13250105", new BigDecimal(3817.80999999999995)); dados.add(dado);
		dado = teste.new Dado("13250105", new BigDecimal(115.689999999999998)); dados.add(dado);
		dado = teste.new Dado("13250109", new BigDecimal(13767.2800000000007)); dados.add(dado);
		dado = teste.new Dado("13250109", new BigDecimal(19484.75)); dados.add(dado);
		dado = teste.new Dado("13250109", new BigDecimal(809.840000000000032)); dados.add(dado);
		dado = teste.new Dado("13250109", new BigDecimal(34061.8700000000026)); dados.add(dado);
		dado = teste.new Dado("13250110", new BigDecimal(23485.3600000000006)); dados.add(dado);
		dado = teste.new Dado("13250199", new BigDecimal(33427.8799999999974)); dados.add(dado);
		dado = teste.new Dado("13250200", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("13250299", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("13250299", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("13250299", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("13250299", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("13300000", new BigDecimal(313742.450000000012)); dados.add(dado);
		dado = teste.new Dado("13330000", new BigDecimal(300797.739999999991)); dados.add(dado);
		dado = teste.new Dado("13330100", new BigDecimal(300797.739999999991)); dados.add(dado);
		dado = teste.new Dado("13390000", new BigDecimal(12944.7099999999991)); dados.add(dado);
		dado = teste.new Dado("13400000", new BigDecimal(8445.46999999999935)); dados.add(dado);
		dado = teste.new Dado("13400300", new BigDecimal(8445.46999999999935)); dados.add(dado);
		dado = teste.new Dado("13900000", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("13900000", new BigDecimal(25149)); dados.add(dado);
		dado = teste.new Dado("13900000", new BigDecimal(25149)); dados.add(dado);
		dado = teste.new Dado("15000000", new BigDecimal(182792.470000000001)); dados.add(dado);
		dado = teste.new Dado("15200000", new BigDecimal(182792.470000000001)); dados.add(dado);
		dado = teste.new Dado("15202800", new BigDecimal(182792.470000000001)); dados.add(dado);
		dado = teste.new Dado("16000000", new BigDecimal(16888903.4800000004)); dados.add(dado);
		dado = teste.new Dado("16000500", new BigDecimal(4868565.98000000045)); dados.add(dado);
		dado = teste.new Dado("16000501", new BigDecimal(2547616.47999999998)); dados.add(dado);
		dado = teste.new Dado("16000503", new BigDecimal(575339.630000000005)); dados.add(dado);
		dado = teste.new Dado("16000510", new BigDecimal(918306.689999999944)); dados.add(dado);
		dado = teste.new Dado("16000599", new BigDecimal(827303.180000000051)); dados.add(dado);
		dado = teste.new Dado("16001300", new BigDecimal(120337.5)); dados.add(dado);
		dado = teste.new Dado("16001301", new BigDecimal(113164.630000000005)); dados.add(dado);
		dado = teste.new Dado("16001302", new BigDecimal(7172.86999999999989)); dados.add(dado);
		dado = teste.new Dado("16001400", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("16001400", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("16004100", new BigDecimal(115.689999999999998)); dados.add(dado);
		dado = teste.new Dado("16004100", new BigDecimal(49515.9400000000023)); dados.add(dado);
		dado = teste.new Dado("16004100", new BigDecimal(184065.079999999987)); dados.add(dado);
		dado = teste.new Dado("16004100", new BigDecimal(1041.23000000000002)); dados.add(dado);
		dado = teste.new Dado("16004100", new BigDecimal(14577.1200000000008)); dados.add(dado);
		dado = teste.new Dado("16004100", new BigDecimal(15734.0300000000007)); dados.add(dado);
		dado = teste.new Dado("16004100", new BigDecimal(10345542.0600000005)); dados.add(dado);
		dado = teste.new Dado("16004100", new BigDecimal(10080492.9700000007)); dados.add(dado);
		dado = teste.new Dado("16004300", new BigDecimal(404920.039999999979)); dados.add(dado);
		dado = teste.new Dado("16004400", new BigDecimal(649607.439999999944)); dados.add(dado);
		dado = teste.new Dado("16004600", new BigDecimal(138829.720000000001)); dados.add(dado);
		dado = teste.new Dado("16004800", new BigDecimal(140333.709999999992)); dados.add(dado);
		dado = teste.new Dado("16009900", new BigDecimal(220767.029999999999)); dados.add(dado);
		dado = teste.new Dado("16009900", new BigDecimal(220767.029999999999)); dados.add(dado);
		dado = teste.new Dado("16009900", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("16009900", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("17000000", new BigDecimal(210023054.020000011)); dados.add(dado);
		dado = teste.new Dado("17200000", new BigDecimal(202907473.199999988)); dados.add(dado);
		dado = teste.new Dado("17210000", new BigDecimal(87655866.3299999982)); dados.add(dado);
		dado = teste.new Dado("17210100", new BigDecimal(32571172.120000001)); dados.add(dado);
		dado = teste.new Dado("17210102", new BigDecimal(32176012.7600000016)); dados.add(dado);
		dado = teste.new Dado("17210105", new BigDecimal(395159.359999999986)); dados.add(dado);
		dado = teste.new Dado("17212200", new BigDecimal(373872.140000000014)); dados.add(dado);
		dado = teste.new Dado("17212270", new BigDecimal(373872.140000000014)); dados.add(dado);
		dado = teste.new Dado("17213300", new BigDecimal(945747.010000000009)); dados.add(dado);
		dado = teste.new Dado("17213300", new BigDecimal(409864.280000000028)); dados.add(dado);
		dado = teste.new Dado("17213300", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("17213300", new BigDecimal(1508005.42999999993)); dados.add(dado);
		dado = teste.new Dado("17213300", new BigDecimal(117700.789999999994)); dados.add(dado);
		dado = teste.new Dado("17213300", new BigDecimal(19192179.1700000018)); dados.add(dado);
		dado = teste.new Dado("17213300", new BigDecimal(1810637.82000000007)); dados.add(dado);
		dado = teste.new Dado("17213300", new BigDecimal(588692.75)); dados.add(dado);
		dado = teste.new Dado("17213300", new BigDecimal(627839.040000000037)); dados.add(dado);
		dado = teste.new Dado("17213300", new BigDecimal(531236.689999999944)); dados.add(dado);
		dado = teste.new Dado("17213300", new BigDecimal(4979298.04999999981)); dados.add(dado);
		dado = teste.new Dado("17213300", new BigDecimal(3867193.04999999981)); dados.add(dado);
		dado = teste.new Dado("17213300", new BigDecimal(899450.680000000051)); dados.add(dado);
		dado = teste.new Dado("17213300", new BigDecimal(920611.479999999981)); dados.add(dado);
		dado = teste.new Dado("17213300", new BigDecimal(1689058.21999999997)); dados.add(dado);
		dado = teste.new Dado("17213300", new BigDecimal(4800591.58000000007)); dados.add(dado);
		dado = teste.new Dado("17213300", new BigDecimal(44951434.0200000033)); dados.add(dado);
		dado = teste.new Dado("17213300", new BigDecimal(535567.819999999949)); dados.add(dado);
		dado = teste.new Dado("17213300", new BigDecimal(1527760.15999999992)); dados.add(dado);
		dado = teste.new Dado("17213400", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("17213400", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("17213400", new BigDecimal(223369.670000000013)); dados.add(dado);
		dado = teste.new Dado("17213400", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("17213400", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("17213400", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("17213400", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("17213400", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("17213400", new BigDecimal(839207.310000000056)); dados.add(dado);
		dado = teste.new Dado("17213400", new BigDecimal(174150.130000000005)); dados.add(dado);
		dado = teste.new Dado("17213400", new BigDecimal(715186.079999999958)); dados.add(dado);
		dado = teste.new Dado("17213400", new BigDecimal(877732.550000000047)); dados.add(dado);
		dado = teste.new Dado("17213400", new BigDecimal(693667.459999999963)); dados.add(dado);
		dado = teste.new Dado("17213400", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("17213400", new BigDecimal(346233.989999999991)); dados.add(dado);
		dado = teste.new Dado("17213400", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("17213400", new BigDecimal(260506.619999999995)); dados.add(dado);
		dado = teste.new Dado("17213400", new BigDecimal(222444.140000000014)); dados.add(dado);
		dado = teste.new Dado("17213400", new BigDecimal(1329044.85000000009)); dados.add(dado);
		dado = teste.new Dado("17213400", new BigDecimal(5681542.79999999981)); dados.add(dado);
		dado = teste.new Dado("17213400", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("17213500", new BigDecimal(2669248.87999999989)); dados.add(dado);
		dado = teste.new Dado("17213501", new BigDecimal(578445.109999999986)); dados.add(dado);
		dado = teste.new Dado("17213502", new BigDecimal(51945.4499999999971)); dados.add(dado);
		dado = teste.new Dado("17213503", new BigDecimal(1509907.37999999989)); dados.add(dado);
		dado = teste.new Dado("17213504", new BigDecimal(66522.5899999999965)); dados.add(dado);
		dado = teste.new Dado("17213504", new BigDecimal(91859.0099999999948)); dados.add(dado);
		dado = teste.new Dado("17213504", new BigDecimal(158381.600000000006)); dados.add(dado);
		dado = teste.new Dado("17213599", new BigDecimal(370569.340000000026)); dados.add(dado);
		dado = teste.new Dado("17213600", new BigDecimal(547798.969999999972)); dados.add(dado);
		dado = teste.new Dado("17219900", new BigDecimal(860797.400000000023)); dados.add(dado);
		dado = teste.new Dado("17219900", new BigDecimal(860797.400000000023)); dados.add(dado);
		dado = teste.new Dado("17219900", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("17220000", new BigDecimal(49251606.8699999973)); dados.add(dado);
		dado = teste.new Dado("17220100", new BigDecimal(47592916.1000000015)); dados.add(dado);
		dado = teste.new Dado("17220101", new BigDecimal(44684104.1300000027)); dados.add(dado);
		dado = teste.new Dado("17220102", new BigDecimal(1574712.98999999999)); dados.add(dado);
		dado = teste.new Dado("17220104", new BigDecimal(1052506.02000000002)); dados.add(dado);
		dado = teste.new Dado("17220113", new BigDecimal(281592.960000000021)); dados.add(dado);
		dado = teste.new Dado("17220113", new BigDecimal(281592.960000000021)); dados.add(dado);
		dado = teste.new Dado("17220113", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("17223300", new BigDecimal(618602.130000000005)); dados.add(dado);
		dado = teste.new Dado("17223300", new BigDecimal(559737.790000000037)); dados.add(dado);
		dado = teste.new Dado("17223300", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("17223300", new BigDecimal(480350.849999999977)); dados.add(dado);
		dado = teste.new Dado("17223300", new BigDecimal(1658690.77000000002)); dados.add(dado);
		dado = teste.new Dado("17240000", new BigDecimal(66000000)); dados.add(dado);
		dado = teste.new Dado("17240100", new BigDecimal(35212940.4299999997)); dados.add(dado);
		dado = teste.new Dado("17240200", new BigDecimal(30787059.5700000003)); dados.add(dado);
		dado = teste.new Dado("17300000", new BigDecimal(82603.6999999999971)); dados.add(dado);
		dado = teste.new Dado("17600000", new BigDecimal(7032977.12000000011)); dados.add(dado);
		dado = teste.new Dado("17610000", new BigDecimal(2541445.93000000017)); dados.add(dado);
		dado = teste.new Dado("17610100", new BigDecimal(1116422.39999999991)); dados.add(dado);
		dado = teste.new Dado("17610200", new BigDecimal(557337.729999999981)); dados.add(dado);
		dado = teste.new Dado("17610300", new BigDecimal(520611.479999999981)); dados.add(dado);
		dado = teste.new Dado("17610400", new BigDecimal(347074.320000000007)); dados.add(dado);
		dado = teste.new Dado("17620000", new BigDecimal(4491531.19000000041)); dados.add(dado);
		dado = teste.new Dado("17620100", new BigDecimal(2878750.08999999985)); dados.add(dado);
		dado = teste.new Dado("17620200", new BigDecimal(578457.199999999953)); dados.add(dado);
		dado = teste.new Dado("17620200", new BigDecimal(639239.160000000033)); dados.add(dado);
		dado = teste.new Dado("17620200", new BigDecimal(1217696.3600000001)); dados.add(dado);
		dado = teste.new Dado("17629900", new BigDecimal(395084.739999999991)); dados.add(dado);
		dado = teste.new Dado("19000000", new BigDecimal(510755.739999999991)); dados.add(dado);
		dado = teste.new Dado("19100000", new BigDecimal(329220.809999999998)); dados.add(dado);
		dado = teste.new Dado("19140000", new BigDecimal(4280.57999999999993)); dados.add(dado);
		dado = teste.new Dado("19140100", new BigDecimal(4280.57999999999993)); dados.add(dado);
		dado = teste.new Dado("19140101", new BigDecimal(4280.57999999999993)); dados.add(dado);
		dado = teste.new Dado("19190000", new BigDecimal(324940.229999999981)); dados.add(dado);
		dado = teste.new Dado("19191500", new BigDecimal(316726.130000000005)); dados.add(dado);
		dado = teste.new Dado("19199900", new BigDecimal(8214.10000000000036)); dados.add(dado);
		dado = teste.new Dado("19200000", new BigDecimal(156314.179999999993)); dados.add(dado);
		dado = teste.new Dado("19210000", new BigDecimal(150645.299999999988)); dados.add(dado);
		dado = teste.new Dado("19219900", new BigDecimal(150645.299999999988)); dados.add(dado);
		dado = teste.new Dado("19220000", new BigDecimal(5668.88000000000011)); dados.add(dado);
		dado = teste.new Dado("19229900", new BigDecimal(5668.88000000000011)); dados.add(dado);
		dado = teste.new Dado("19300000", new BigDecimal(2892.30000000000018)); dados.add(dado);
		dado = teste.new Dado("19320000", new BigDecimal(2892.30000000000018)); dados.add(dado);
		dado = teste.new Dado("19329900", new BigDecimal(2892.30000000000018)); dados.add(dado);
		dado = teste.new Dado("19329901", new BigDecimal(2892.30000000000018)); dados.add(dado);
		dado = teste.new Dado("19900000", new BigDecimal(22328.4500000000007)); dados.add(dado);
		dado = teste.new Dado("19909999", new BigDecimal(22328.4500000000007)); dados.add(dado);
		dado = teste.new Dado("21000000", new BigDecimal(192431.440000000002)); dados.add(dado);
		dado = teste.new Dado("21100000", new BigDecimal(192431.440000000002)); dados.add(dado);
		dado = teste.new Dado("21190000", new BigDecimal(192431.440000000002)); dados.add(dado);
		dado = teste.new Dado("22000000", new BigDecimal(106241.869999999995)); dados.add(dado);
		dado = teste.new Dado("22100000", new BigDecimal(59842.9700000000012)); dados.add(dado);
		dado = teste.new Dado("22190000", new BigDecimal(59842.9700000000012)); dados.add(dado);
		dado = teste.new Dado("22200000", new BigDecimal(46398.9000000000015)); dados.add(dado);
		dado = teste.new Dado("22290000", new BigDecimal(46398.9000000000015)); dados.add(dado);
		dado = teste.new Dado("24000000", new BigDecimal(56539333.3299999982)); dados.add(dado);
		dado = teste.new Dado("24200000", new BigDecimal(1189192.30000000005)); dados.add(dado);
		dado = teste.new Dado("24210000", new BigDecimal(1189192.30000000005)); dados.add(dado);
		dado = teste.new Dado("24210100", new BigDecimal(610735.099999999977)); dados.add(dado);
		dado = teste.new Dado("24210200", new BigDecimal(578457.199999999953)); dados.add(dado);
		dado = teste.new Dado("24700000", new BigDecimal(55350141.0300000012)); dados.add(dado);
		dado = teste.new Dado("24710000", new BigDecimal(26360728.4499999993)); dados.add(dado);
		dado = teste.new Dado("24710100", new BigDecimal(1749886.75)); dados.add(dado);
		dado = teste.new Dado("24710200", new BigDecimal(1026677.35999999999)); dados.add(dado);
		dado = teste.new Dado("24710300", new BigDecimal(3202027.12000000011)); dados.add(dado);
		dado = teste.new Dado("24710400", new BigDecimal(3904486.58999999985)); dados.add(dado);
		dado = teste.new Dado("24710500", new BigDecimal(1318303.94999999995)); dados.add(dado);
		dado = teste.new Dado("24710500", new BigDecimal(9955996.90000000037)); dados.add(dado);
		dado = teste.new Dado("24710500", new BigDecimal(11274300.8499999996)); dados.add(dado);
		dado = teste.new Dado("24719900", new BigDecimal(5203349.78000000026)); dados.add(dado);
		dado = teste.new Dado("24720000", new BigDecimal(28989412.5799999982)); dados.add(dado);
		dado = teste.new Dado("24720100", new BigDecimal(3963342.29999999981)); dados.add(dado);
		dado = teste.new Dado("24720200", new BigDecimal(4253126.21999999974)); dados.add(dado);
		dado = teste.new Dado("24720300", new BigDecimal(3661622.49000000022)); dados.add(dado);
		dado = teste.new Dado("24720400", new BigDecimal(3280800.99000000022)); dados.add(dado);
		dado = teste.new Dado("24720500", new BigDecimal(711458.400000000023)); dados.add(dado);
		dado = teste.new Dado("24720500", new BigDecimal(9984171.24000000022)); dados.add(dado);
		dado = teste.new Dado("24720500", new BigDecimal(10695629.6400000006)); dados.add(dado);
		dado = teste.new Dado("24729900", new BigDecimal(3134890.93999999994)); dados.add(dado);
		dado = teste.new Dado("25000000", new BigDecimal(0)); dados.add(dado);
		dado = teste.new Dado("25900000", new BigDecimal(0)); dados.add(dado);*/
		
		// System.out.println(dados.size());
		
		/*for (Dado x : dados) {
			
			for (int i = 0; i < x.codigo.length(); i++) {
				
				String str = x.codigo.substring(0, i + 1);
				
				if (teste.possuiDescendentes(str, dados)) {
					
					System.out.println("Descendentes de " + x.codigo);
					
					teste.descendentes(str, dados).forEach(nr -> System.out.println(nr.codigo));
				}
			}
		}*/
		
		System.out.println(teste.recursiva(dados.get(0), dados));
		
		// System.out.println(teste.xx(5));
		
		
		
		System.out.println(teste.xy("qwerty"));
		
		Pattern pattern = Pattern.compile(".*([1-9]+)");
		Matcher m = pattern.matcher("11008080");
				
		if (m.find()) {
			System.out.println(m.start() + " " + m.end());
		}
	}
	
	class Dado {
		
		String codigo;
		BigDecimal valor;
		
		public Dado(String codigo, BigDecimal valor) {
			
			this.codigo = codigo;
			this.valor = valor;
		}
	}
}
