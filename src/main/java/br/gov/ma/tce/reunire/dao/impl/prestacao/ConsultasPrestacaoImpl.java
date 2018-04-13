package br.gov.ma.tce.reunire.dao.impl.prestacao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import br.gov.ma.tce.reunire.dao.impl.PrestacaoDaoImpl;

@Stateless
public class ConsultasPrestacaoImpl<T> extends PrestacaoDaoImpl<T> {
	
	public T byId(Class<T> clazz, Integer id) {
		return entityManager.find(clazz, id);	
	}
	
	public List<T> findAll(Class<T> clazz) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(clazz);
		criteria.select(criteria.from(clazz));
		
		return entityManager.createQuery(criteria).getResultList();
	}
}
