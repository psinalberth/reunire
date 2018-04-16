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
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM02VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM02DaoImpl extends PrestacaoDaoImpl<RelatorioCAM02VO> implements DemonstrativoDao<RelatorioCAM02VO> {

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM02VO> recuperaDados(Map<String, Object> params) {
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		String sql = 
				
		"	select unidade_id, cnpj_concedente, cnpj_convenente, data_celebracao, " + 
		"		valor, objeto, inicio_prazo, fim_prazo, situacao, cam02_id " +
		"	from prestacao.cam02 cam " +
		"	where cam.unidade_id in (:unidades) and " +
		"	((:modulo is null) or (cam.modulo_id = :modulo)) ";
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", 1)
				.getResultList();
		
		List<RelatorioCAM02VO> dados = new ArrayList<RelatorioCAM02VO>(rows.size());
		
		params.put("exercicio", 2017);
		
		for (Object [] row : rows) {
			
			RelatorioCAM02VO dado = new RelatorioCAM02VO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			dado.setConcedente(String.valueOf(row[1]));
			dado.setConvenente(String.valueOf(row[2]));
			
			dado.setDataCelebracao(getFormatador((Date) row[3]));
			
			
			dado.setValor(row[4] != null ? new BigDecimal(row[4].toString()) : null);
			dado.setObjeto(String.valueOf(row[5]));
			
			if(row[6] != null && row[7] != null) {
				dado.setInicioPrazo(getFormatador((Date) row[6]));
				dado.setFimPrazo(getFormatador((Date) row[7]));
			}
			
			dado.setSituacao(String.valueOf(row[8]));
			dado.setId(Integer.parseInt(String.valueOf(row[9])));
			
			
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
		return "relatoriocam02.jasper";
	}
}
