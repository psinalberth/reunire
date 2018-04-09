package br.gov.ma.tce.reunire.dao.impl.gestor;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import br.gov.ma.tce.reunire.dao.impl.GestoresDaoImpl;

@Stateless
public class ConsultasGestoresImpl<T> extends GestoresDaoImpl<T> {
	
	public T byId(Class<T> clazz, Integer id) {
		return entityManager.find(clazz, id);	
	}
	
	public List<T> findAll(Class<T> clazz) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(clazz);
		criteria.select(criteria.from(clazz));
		
		return entityManager.createQuery(criteria).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAllByEnte(Class<T> clazz, Integer id) {
		
		String sql =  "from " + clazz.getSimpleName() + " obj where obj.ente.id = :ente";
		return entityManager.createQuery(sql).setParameter("ente", id).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAllByOrgao(Class<T> clazz, Integer id) {
		
		String sql =  "from " + clazz.getSimpleName() + " obj where obj.orgao.id = :orgao";
		return entityManager.createQuery(sql).setParameter("orgao", id).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAllByPoder(Class<T> clazz, Integer id) {
		
		String sql =  "from " + clazz.getSimpleName() + " obj where obj.poder.id = :poder";
		return entityManager.createQuery(sql).setParameter("poder", id).getResultList();
	}
}