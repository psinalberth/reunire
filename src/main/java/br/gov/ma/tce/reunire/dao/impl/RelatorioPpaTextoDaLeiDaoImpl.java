package br.gov.ma.tce.reunire.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.model.vo.PpaTextoDaLeiVO;

@Stateless
public class RelatorioPpaTextoDaLeiDaoImpl extends SaeDaoImpl<PpaTextoDaLeiVO> implements DemonstrativoDao<PpaTextoDaLeiVO>{
	
	@SuppressWarnings("unchecked")
	public List<PpaTextoDaLeiVO> recuperaDados(Integer ente, Integer orgao, Integer unidadeGestora, Integer poder, Integer exercicio) {
		
		String sql = "SELECT ID_PPA, ENTE, TEXTO_LEI FROM SAE_PPA " + 
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
			//ppa.setTextoDaLei(ppaTextoDaLeiVO[2].toString());
			
			listaPpaTextoDaLeiVO.add(ppa);
		}
		
		return listaPpaTextoDaLeiVO;
	}

	@Override
	public String getNomeRelatorio() {
		return "rel_ppa_texto_lei.jasper";
	}

	@Override
	public List<PpaTextoDaLeiVO> recuperaDados(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}
}
