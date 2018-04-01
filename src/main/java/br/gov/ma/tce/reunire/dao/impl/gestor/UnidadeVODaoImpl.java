package br.gov.ma.tce.reunire.dao.impl.gestor;

import java.util.List;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.impl.GestoresDaoImpl;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;

@Stateless
public class UnidadeVODaoImpl extends GestoresDaoImpl<UnidadeVO> {
	
	public UnidadeVO byId(Integer id) {
		return entityManager.find(UnidadeVO.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<UnidadeVO> byEnte(Integer ente) {
		
		String sql = 
				
		"select " +
			"u.UNIDADE_ID, u.NOME, u.ORGAO_ID, o.ENTE_ID from GESTOR.UNIDADE u, GESTOR.ORGAO o " +
		"where " +
			"u.ORGAO_ID = o.ORGAO_ID and " +
			"o.ENTE_ID = :ente";
		
		return entityManager.createNativeQuery(sql, UnidadeVO.class).setParameter("ente", ente).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<UnidadeVO> byOrgao(Integer orgao) {
		
		String sql =
		
		"select " +
			"u.UNIDADE_ID, u.NOME, u.ORGAO_ID, o.ENTE_ID from GESTOR.UNIDADE u, GESTOR.ORGAO o " +
		"where " +
			"u.ORGAO_ID = :orgao";
		
		return entityManager.createNativeQuery(sql, UnidadeVO.class).setParameter("orgao", orgao).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<UnidadeVO> byPoder(Integer ente, Integer poder) {
		
		String sql =
		
		"select " + 
			"u.UNIDADE_ID, u.NOME, o.ORGAO_ID, o.ENTE_ID from GESTOR.UNIDADE u, GESTOR.ORGAO o, GESTOR.TIPO_ORGAO t " +
		"where " +
			"u.ORGAO_ID = o.ORGAO_ID and " +
			"o.ENTE_ID = :ente and " +
			"o.TIPO_ORGAO_ID = t.TIPO_ORGAO_ID and " +
			"t.PODER_ID = :poder";
		
		return entityManager.createNativeQuery(sql, UnidadeVO.class).setParameter("ente", ente).setParameter("poder", poder).getResultList();
	}
}
