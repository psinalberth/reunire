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
					 "WHERE ENTE = :ente ";
		
		List<PpaTextoDaLeiVO> rows =  entityManager.createNativeQuery(sql, PpaTextoDaLeiVO.class)
				.setParameter("ente", 999999)
				.getResultList();
		
		List<PpaTextoDaLeiVO> listaPpaTextoDaLeiVO = new ArrayList<>();
		
		for (PpaTextoDaLeiVO ppaTextoDaLeiVO : rows) {
			
			PpaTextoDaLeiVO ppa = new PpaTextoDaLeiVO();
			
			ppa.setId(ppaTextoDaLeiVO.getId());
			
			listaPpaTextoDaLeiVO.add(ppa);
		}
		
		return listaPpaTextoDaLeiVO;
	}

	@Override
	public String getNomeRelatorio() {
		return null;
	}
}
