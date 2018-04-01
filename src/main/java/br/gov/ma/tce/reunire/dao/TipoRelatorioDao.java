package br.gov.ma.tce.reunire.dao;

import java.util.List;

import br.gov.ma.tce.reunire.model.TipoRelatorio;

public interface TipoRelatorioDao {
	
	public TipoRelatorio byId(int id);
	
	public List<TipoRelatorio> findAll();
}
