package br.gov.ma.tce.reunire.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import br.gov.ma.tce.reunire.dao.TipoRelatorioDao;
import br.gov.ma.tce.reunire.model.TipoRelatorio;

@Stateless
public class TipoRelatorioDaoImpl extends PrestacaoDaoImpl<TipoRelatorio> implements TipoRelatorioDao {

	@Override
	public TipoRelatorio byId(int id) {
		return entityManager.find(TipoRelatorio.class, id);
	}

	@Override
	public List<TipoRelatorio> findAll() {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<TipoRelatorio> cq = cb.createQuery(TipoRelatorio.class);
		cq.select(cq.from(TipoRelatorio.class));
		
		return entityManager.createQuery(cq).getResultList();
	}
}