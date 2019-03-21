package br.gov.ma.tce.reunire.dao.impl.cam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM01VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM01DaoImpl extends PrestacaoDaoImpl<RelatorioCAM01VO> implements DemonstrativoDao<RelatorioCAM01VO> {

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM01VO> recuperaDados(Map<String, Object> params) {
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		String sql = 
				
		"select " +
			"cam.unidade_id, " + 
			"(case when vw.id_natureza_receita is not null then vw.codigo_natureza_receita else " + 
			"regexp_replace(lpad(cam.nr, 8, '0'), '([[:digit:]]{1})([[:digit:]]{1})([[:digit:]]{1})([[:digit:]]{1})([[:digit:]]{2})([[:digit:]]{2})', '\\1.\\2.\\3.\\4.\\5.\\6') " +
			"end) codigo_natureza_receita, " + 
			"coalesce(upper(case when vw.id_natureza_receita is not null then vw.descricao else nr.nome end), 'CLASSIFICAÇÃO DESCONHECIDA') descricao, " + 
			"cam.x_menos_3, cam.x_menos_2, cam.x_menos_1, cam.x, cam.x_mais_1, cam.x_mais_2 " +
		"from " + 
			"sae.vw_natureza_receita vw " +
		"right join " + 
			"(select " + 
				"cam.unidade_id, regexp_replace(cam.codigo_natureza_receita, '[.]', '', 'g') nr, " +
				"sum(cam.ano_x_menos_3) x_menos_3, sum(cam.ano_x_menos_2) x_menos_2, sum(cam.ano_x_menos_1) x_menos_1, " +
				"sum(cam.ano_x) x, sum(cam.ano_x_mais_1) x_mais_1, sum(cam.ano_x_mais_2) x_mais_2 " +
			"from " +
				"prestacao.cam01 cam " +
			"where " +
				"cam.unidade_id in (:unidades) and " +
				"((:modulo is null) or (cam.modulo_id = :modulo)) " +
			"group by " +
				"cam.unidade_id, regexp_replace(cam.codigo_natureza_receita, '[.]', '', 'g') " +
			"order by " +
				"cam.unidade_id, nr asc) cam on " +
			"regexp_replace(vw.codigo_natureza_receita, '[.]', '', 'g') = cam.nr and vw.ativo = 'S' " +
		"left join remessa.natureza_receita nr on " +
			"cam.nr = regexp_replace(nr.codigo, '[.]', '', 'g') " +
		"order by " +
			"cam.unidade_id, codigo_natureza_receita";
		
		String schema = params.get("exercicio") != null && ((Integer)params.get("exercicio")).equals(new Integer(2018)) ? "prestacao2018" : "prestacao";
		sql = sql.replaceAll("prestacao", schema);
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidades", listaIdsUnidades)
				.setParameter("modulo", 1)
				.getResultList();
		
		List<RelatorioCAM01VO> dados = new ArrayList<RelatorioCAM01VO>(rows.size());
		
		params.put("exercicio", params.get("exercicio"));
		
		for (Object [] row : rows) {
			
			RelatorioCAM01VO dado = new RelatorioCAM01VO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			dado.setCodigoNaturezaReceita(String.valueOf(row[1]));
			dado.setDescricao(String.valueOf(row[2]));
			dado.setValorAnoMenos3(row[3] != null ? new BigDecimal(row[3].toString()) : null);
			dado.setValorAnoMenos2(row[4] != null ? new BigDecimal(row[4].toString()) : null);
			dado.setValorAnoMenos1(row[5] != null ? new BigDecimal(row[5].toString()) : null);
			dado.setValorAno(row[6] != null ? new BigDecimal(row[6].toString()) : null);
			dado.setValorAnoMais1(row[7] != null ? new BigDecimal(row[7].toString()) : null);
			dado.setValorAnoMais2(row[8] != null ? new BigDecimal(row[8].toString()) : null);
			
			dados.add(dado);
		}
		
		return dados;
	}

	@Override
	public String getNomeRelatorio() {
		return "relatoriocam01.jasper";
	}
}
