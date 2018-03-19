package br.gov.ma.tce.reunire.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import br.gov.ma.tce.reunire.dao.DemonstrativoDao;
import br.gov.ma.tce.reunire.model.vo.RelatorioPessoaVO;

@Stateless
public class RelatorioPessoaDaoImpl implements DemonstrativoDao<RelatorioPessoaVO> {
	
	EntityManager manager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RelatorioPessoaVO> recuperaDados(Integer ente, Integer orgao, Integer unidadeGestora,
			Integer exercicio) {
		
		String sql = 
		"";
		
		return manager.createNativeQuery(sql, RelatorioPessoaVO.class).getResultList();
	}

	@Override
	public String getNomeRelatorio() {
		return null;
	}
}