package br.gov.ma.tce.reunire.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.model.vo.PpaTextoDaLeiVO;

@Stateless
public class RelatorioPpaTextoDaLeiDaoImpl extends SAEDAOImpl<PpaTextoDaLeiVO> implements DemonstrativoDao<PpaTextoDaLeiVO>{
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PpaTextoDaLeiVO> recuperaDados(Integer ente, Integer orgao, Integer unidadeGestora, Integer exercicio) {
		
		String sql = "SELECT ID_PPA, ENTE FROM SAE_PPA " + 
					 "WHERE ENTE = 999999 ";
		
		List<Object[]> rows =  entityManager.createNativeQuery(sql)
				.getResultList();
		
		List<PpaTextoDaLeiVO> listaPpaTextoDaLeiVO = new ArrayList<>();
		
		for (Object[] ppaTextoDaLeiVO : rows) {
			
			PpaTextoDaLeiVO ppa = new PpaTextoDaLeiVO();
			
			Integer i = Integer.valueOf(ppaTextoDaLeiVO[0].toString());
			System.out.println(i);
			
			Integer i2 = Integer.valueOf(ppaTextoDaLeiVO[1].toString());
			
			ppa.setId((int) i);
			ppa.setEnte((int) i2);
			
			listaPpaTextoDaLeiVO.add(ppa);
		}
		
		return listaPpaTextoDaLeiVO;
	}

	@Override
	public String getNomeRelatorio() {
		return "rel_ppa_texto_lei.jasper";
	}
}
