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

@Stateless
public class RelatorioB01DaoImpl extends PrestacaoDaoImpl<RelatorioB01VO> implements DemonstrativoDao<RelatorioB01VO> {

	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioB01VO> recuperaDados(Integer ente, Integer orgao, Integer unidadeGestora, Integer poder,
			Integer exercicio) {
		
		String sql = null;
				
		try {
			sql = new String(Files.readAllBytes(Paths.get(this.getClass().getResource("relatoriobo01.sql").getFile())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<Integer> listaIdsUnidades = recuperarIdsUnidades(ente, orgao, poder, unidadeGestora);
		
		List<Object[]> rows = entityManager.createNativeQuery(sql).setParameter("unidades", listaIdsUnidades).getResultList();

		List<RelatorioB01VO> dados = new ArrayList<RelatorioB01VO>(rows.size());

		
		for (Object[] row : rows) {
			
			RelatorioB01VO dado = new RelatorioB01VO();
			
			dado.setTotal(String.valueOf(row[0]));
			//dado.setSubtotal(String.valueOf(row[1]));
			dado.setTotalCategoria(String.valueOf(row[1]));
			dado.setCategoria(String.valueOf(row[2]));
			dado.setOrigem(String.valueOf(row[3]));
			dado.setEspecie(String.valueOf(row[4]));
			dado.setPrevisaoInicial(row[5] != null ? new BigDecimal(row[5].toString()) : null);
			dado.setPrevisaoAtualizada(row[6] != null ? new BigDecimal(row[6].toString()) : null);
			dado.setReceitasRealizadas(row[7] != null ? new BigDecimal(row[7].toString()) : null);
			
			dados.add(dado);
		}
		
		return dados;
	}

	@Override
	public String getNomeRelatorio() {
		return "relatoriobo2.jasper";
	}
}
