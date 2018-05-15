package br.gov.ma.tce.reunire.dao.impl.cam;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM24VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM24DaoImpl extends PrestacaoDaoImpl<RelatorioCAM24VO> implements DemonstrativoDao<RelatorioCAM24VO> {

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM24VO> recuperaDados(Map<String, Object> params) {
		
		String sql = "select c.unidade_id, to_char(c.competencia,'dd/MM/yyyy') competencia, c.unidade_utilizacao_id, c.tipo_folha, c.cota_empregado, c.cota_empregador, c.regime, c.numero_documento " + 
				"from prestacao.cam24 c " + 
				"where c.unidade_id in(:unidade) and c.competencia is not null "+
				"group by c.unidade_id, competencia, c.unidade_utilizacao_id, c.tipo_folha, c.cota_empregado, c.cota_empregador, c.regime, c.numero_documento " +
				"order by c.unidade_id, competencia, c.unidade_utilizacao_id, c.tipo_folha";
		
		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		List<RelatorioCAM24VO> dados = new ArrayList<>();
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidade", listaIdsUnidades)
				.getResultList();
		
		for(Object[] row : rows) {
			
			RelatorioCAM24VO dado = new RelatorioCAM24VO();
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			
			if (unidade != null) {
				
				dado.setIdOrgao(unidade.get().getOrgao().getId());
				dado.setDescricaoOrgao(unidade.get().getOrgao().getNome());
			}
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			
			Date data = null;
			try {
				data = formato.parse(row[1].toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			dado.setCompetencia(data);
			dado.setUnidadeOrcamentaria(Integer.parseInt(row[2].toString()));
			dado.setFolhaPagamento(row[3].toString());
			dado.setCota_empregado(new BigDecimal(Double.parseDouble(row[4].toString())));
			dado.setCota_empregador(new BigDecimal(Double.parseDouble(row[5].toString())));
			dado.setRegime(row[6].toString());
			dado.setDocumento(row[7].toString());
			dados.add(dado);	
		}
		
		dados.sort((d1, d2) -> d1.getIdOrgao().compareTo(d2.getIdOrgao()));
		
		return dados;
	}

	@Override
	public String getNomeRelatorio() {
		return "relatorioCAM24.jasper";
	}

}
