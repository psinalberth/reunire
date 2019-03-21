package br.gov.ma.tce.reunire.dao.impl.cam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM16VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM16DaoImpl extends PrestacaoDaoImpl<RelatorioCAM16VO> implements DemonstrativoDao<RelatorioCAM16VO> {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM16VO> recuperaDados(Map<String, Object> params) {
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		String sql = 
				
		"select unidade_id, numero_processo, extract (year from data_fato ) as anoProcesso, " + 
		"	data_fato, data_instauracao, valor_dano, upper(situacao) situacao, " + 
		"	cpf_cnpj_responsavel, cpf_cnpj_responsavel2, cpf_cnpj_responsavel3, " + 
		"	cpf_cnpj_responsavel4, cpf_cnpj_responsavel5, cpf_cnpj_responsavel6, " + 
		"	cpf_cnpj_responsavel7, cpf_cnpj_responsavel8 " + 
		"from prestacao.cam16 cam " + 
		"	where cam.unidade_id in (:unidades) and " +
		"	((:modulo is null) or (cam.modulo_id = :modulo)) ";
		
		String schema = params.get("exercicio") != null && ((Integer)params.get("exercicio")).equals(new Integer(2018)) ? "prestacao2018" : "prestacao";
		sql = sql.replaceAll("prestacao", schema);
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", 1)
				.getResultList();
		
		List<RelatorioCAM16VO> dados = new ArrayList<RelatorioCAM16VO>(rows.size());
		
		params.put("exercicio", params.get("exercicio"));
		
		for (Object [] row : rows) {
			
			RelatorioCAM16VO dado = new RelatorioCAM16VO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			dado.setNumeroProcesso(String.valueOf(row[1]));
			dado.setAnoProcesso(String.valueOf(row[2]).substring(0, 4));
			dado.setDataDoFato(row[3] != null ? getFormatador((Date) row[3]) : null);
			dado.setDataInstauracao(row[4] != null ? getFormatador((Date) row[4]) : null);
			dado.setValorDoDano(toBigDecimal(row[5]));
			dado.setSituacao(String.valueOf(row[6]));
			
			if(row[7] != null) {
				dado.setCpf1(toPessoa(row[7]));
				
			}else if(row[8] != null){
				dado.setCpf2(toPessoa(row[8]));
				
			}else if(row[9] != null) {
				dado.setCpf3(toPessoa(row[9]));
				
			}else if(row[10] != null) {
				dado.setCpf4(toPessoa(row[10]));
				
			}else if(row[11] != null) {
				dado.setCpf5(toPessoa(row[11]));
				
			}else if(row[12] != null) {
				dado.setCpf6(toPessoa(row[12]));
				
			}else if(row[13] != null) {
				dado.setCpf7(toPessoa(row[13]));
				
			}else if(row[14] != null) {	
				dado.setCpf8(toPessoa(row[14]));
			
			}
			
			
			dados.add(dado);
		}
		
		return dados;
	}
	
	public static String getFormatador(Date data) {
		DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		String novaData = formatador.format(data);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		
		if (cal.get(Calendar.YEAR) < 1900)
			return "N/A";
		
		return  novaData;
	}
	
	@Override
	public String getNomeRelatorio() {
		return "relatoriocam16.jasper";
	}
}