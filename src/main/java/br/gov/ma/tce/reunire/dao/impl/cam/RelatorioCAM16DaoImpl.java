package br.gov.ma.tce.reunire.dao.impl.cam;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
		"	data_fato, data_instauracao, valor_dano, situacao, " + 
		"	cpf_cnpj_responsavel, cpf_cnpj_responsavel2, cpf_cnpj_responsavel3, " + 
		"	cpf_cnpj_responsavel4, cpf_cnpj_responsavel5, cpf_cnpj_responsavel6, " + 
		"	cpf_cnpj_responsavel7, cpf_cnpj_responsavel8 " + 
		"from prestacao.cam16 cam " + 
		"	where cam.unidade_id in (:unidades) and " +
		"	((:modulo is null) or (cam.modulo_id = :modulo)) ";
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", 1)
				.getResultList();
		
		List<RelatorioCAM16VO> dados = new ArrayList<RelatorioCAM16VO>(rows.size());
		
		params.put("exercicio", 2017);
		
		for (Object [] row : rows) {
			
			RelatorioCAM16VO dado = new RelatorioCAM16VO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			dado.setNumeroProcesso(String.valueOf(row[1]));
			dado.setAnoProcesso(String.valueOf(row[2]));
			dado.setDataDoFato(getFormatador((Date) row[3]));
			dado.setDataInstauracao(getFormatador((Date) row[4]));
			dado.setValorDoDano(toBigDecimal(row[5]));
			dado.setSituacao(String.valueOf(row[6]));
			dado.setCpf1(String.valueOf(row[7]));
			dado.setCpf2(String.valueOf(row[8]));
			dado.setCpf3(String.valueOf(row[9]));
			dado.setCpf4(String.valueOf(row[10]));
			dado.setCpf5(String.valueOf(row[11]));
			dado.setCpf6(String.valueOf(row[12]));
			dado.setCpf7(String.valueOf(row[13]));
			dado.setCpf8(String.valueOf(row[14]));
			
			dados.add(dado);
		}
		
		return dados;
	}
	
	public static String getFormatador(Date data) {
		DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		String novaData = formatador.format(data);
		return  novaData;
	}
	
	@Override
	public String getNomeRelatorio() {
		return "relatoriocam16.jasper";
	}
}