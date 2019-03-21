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
import br.gov.ma.tce.reunire.model.vo.cam.RelatorioCAM22VO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class RelatorioCAM22DaoImpl extends PrestacaoDaoImpl<RelatorioCAM22VO> implements DemonstrativoDao<RelatorioCAM22VO> {

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioCAM22VO> recuperaDados(Map<String, Object> params) {
		
		String sql = "select c.unidade_id, c.identificacao, c.destinacao, c.valor, c.situacao, to_char(c.data_aquisicao,'dd/MM/yyyy') data_aquisicao " + 
				"from prestacao.cam22 c "
				+ "where c.unidade_id in(:unidade) and c.modulo_id = :modulo "
				+ "group by c.unidade_id, c.identificacao, c.destinacao, c.valor, c.situacao, data_aquisicao "
				+ "order by c.unidade_id, c.identificacao, c.destinacao, c.situacao, data_aquisicao";
		
		String schema = params.get("exercicio") != null && ((Integer)params.get("exercicio")).equals(new Integer(2018)) ? "prestacao2018" : "prestacao";
		sql = sql.replaceAll("prestacao", schema);

		List<UnidadeVO> listaUnidades = recuperarUnidades(params);
		List<Integer> listaIdsUnidades = extrairIds(listaUnidades);
		
		List<RelatorioCAM22VO> dados = new ArrayList<>();
		
		List<Object[]> rows = entityManager.createNativeQuery(sql)
				.setParameter("unidade", listaIdsUnidades)
				.setParameter("modulo", params.get("modulo"))
				.getResultList();
		
		for(Object[] row : rows) {
			
			RelatorioCAM22VO dado = new RelatorioCAM22VO();
			
			dado.setIdUnidade(Integer.parseInt(row[0].toString()));
			
			Optional<UnidadeVO> unidade = listaUnidades.stream().filter(u -> u.getId().equals(Integer.parseInt(String.valueOf(row[0])))).findFirst();
			
			dado.setIdUnidade(Integer.parseInt(String.valueOf(row[0])));
			dado.setDescricaoUnidade(unidade != null ? unidade.get().getNome().toUpperCase() : "");
			
			if (unidade != null) {
				
				dado.setIdOrgao(unidade.get().getOrgao().getId());
				dado.setDescricaoOrgao(unidade.get().getOrgao().getNome());
			}
			
			dado.setIdentificacao(row[1].toString());
			dado.setDestinacao(row[2].toString());
			dado.setValor(new BigDecimal(Double.parseDouble(row[3].toString())));
			dado.setSituacao(row[4].toString());
			
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			
			Date data = null;
			try {
				data = formato.parse(row[5].toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			dado.setDataAquisicao(data);
			dados.add(dado);			
		}
		
		dados.sort((d1, d2) -> d1.getIdOrgao().compareTo(d2.getIdOrgao()));
		
		return dados;
	}

	@Override
	public String getNomeRelatorio() {		
		return "relatoriocam22.jasper";
	}

}
