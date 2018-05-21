package br.gov.ma.tce.reunire.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.gov.ma.tce.reunire.dao.TipoRelatorioDao;
import br.gov.ma.tce.reunire.model.TipoRelatorio;

@Stateless
public class TipoRelatorioDaoImpl extends PrestacaoDaoImpl<TipoRelatorio> implements TipoRelatorioDao {

	@Override
	public TipoRelatorio byId(int id) {
		return entityManager.find(TipoRelatorio.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoRelatorio> findAllByModulo(Integer modulo) {
		
		String sql = 
				
		"select tipo.id_tipo_relatorio, tipo.codigo, tipo.descricao from reunire.tipo_relatorio tipo " +
			"inner join reunire.tipo_relatorio_modulo modulo on " +
				"modulo.id_tipo_relatorio = tipo.id_tipo_relatorio " +
		"where " +
			"modulo.id_modulo = :modulo " +
		"order by " +
			"tipo.codigo";
		
		return entityManager.createNativeQuery(sql, TipoRelatorio.class).setParameter("modulo", modulo).getResultList();
	}

	@Override
	public List<TipoRelatorio> findAll() {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<TipoRelatorio> cq = cb.createQuery(TipoRelatorio.class);
		Root<TipoRelatorio> root = cq.from(TipoRelatorio.class);
		cq.select(root);
		cq.orderBy(cb.asc(root.get("id")));
		
		return entityManager.createQuery(cq).getResultList();
	}
}