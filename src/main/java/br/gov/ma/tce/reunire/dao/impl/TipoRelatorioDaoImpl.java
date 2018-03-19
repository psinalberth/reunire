package br.gov.ma.tce.reunire.dao.impl;

import javax.ejb.Stateless;

import br.gov.ma.tce.reunire.dao.TipoRelatorioDao;
import br.gov.ma.tce.reunire.model.TipoRelatorio;

@Stateless
public class TipoRelatorioDaoImpl extends DAOImpl<TipoRelatorio> implements TipoRelatorioDao {

	@Override
	public TipoRelatorio byId(int id) {
		return entityManager.find(TipoRelatorio.class, id);
	}
}
