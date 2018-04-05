package br.gov.ma.tce.reunire.dao.impl.bo;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;
import br.gov.ma.tce.reunire.model.vo.bo.RelatorioB01VO;
import br.gov.ma.tce.reunire.model.vo.bo.RelatorioB02VO;

@Stateless
public class RelatorioB02DaoImpl extends PrestacaoDaoImpl<RelatorioB02VO> implements DemonstrativoDao<RelatorioB02VO> {

	@Override
	public List<RelatorioB02VO> recuperaDados(Integer ente, Integer orgao, Integer unidadeGestora, Integer poder,
			Integer exercicio) {
		
		String sql = null;
		
		try {
			sql = new String(Files.readAllBytes(Paths.get(this.getClass().getResource("relatoriobo02.sql").getFile())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<Integer> listaIdsUnidades = recuperarIdsUnidades(ente, orgao, poder, unidadeGestora);
		
		List<Object[]> rows = entityManager.createNativeQuery(sql).setParameter("unidades", listaIdsUnidades).getResultList();

		List<RelatorioB02VO> dados = new ArrayList<RelatorioB02VO>(rows.size());

		
		for (Object[] row : rows) {
			
			RelatorioB02VO dado = new RelatorioB02VO();
			
			dado.setTotal(String.valueOf(row[0]));
			//dado.setSubtotal(String.valueOf(row[1]));
			dado.setTotalCategoria(String.valueOf(row[1]));
			dado.setCategoria(String.valueOf(row[2]));
			dado.setGrupo(String.valueOf(row[3]));
			dado.setModalidade(String.valueOf(row[4]));
			dado.setDotacaoInicial(row[5] != null ? new BigDecimal(row[5].toString()) : null);
			dado.setDotacaoAtualizada(row[6] != null ? new BigDecimal(row[6].toString()) : null);
			dado.setDespesasEmpenhadas(row[7] != null ? new BigDecimal(row[7].toString()) : null);
			dado.setDespesasLiquidadas(row[8] != null ? new BigDecimal(row[8].toString()) : null);
			dado.setDespesasPagas(row[9] != null ? new BigDecimal(row[9].toString()) : null);
			//dado.setPrevisaoInicial(row[5] != null ? new BigDecimal(row[5].toString()) : null);
			//dado.setPrevisaoAtualizada(row[6] != null ? new BigDecimal(row[6].toString()) : null);
			//dado.setReceitasRealizadas(row[7] != null ? new BigDecimal(row[7].toString()) : null);
			
			dados.add(dado);
		}
		
		return dados;
	}

	@Override
	public String getNomeRelatorio() {
		return "relatoriobo02.jasper";
	}

}
