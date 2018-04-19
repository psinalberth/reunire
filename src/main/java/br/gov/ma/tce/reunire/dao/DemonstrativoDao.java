package br.gov.ma.tce.reunire.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.gov.ma.tce.reunire.dao.impl.gestor.UnidadeVODaoImpl;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;
import br.gov.ma.tce.reunire.util.Lookup;

public interface DemonstrativoDao<T> {
	
	@SuppressWarnings("all")
	public List<T> recuperaDados(Map<String, Object> params);
	
	public String getNomeRelatorio();
	
	public default List<UnidadeVO> recuperarUnidades(Map<String, Object> params) {
		
		UnidadeVODaoImpl dao = Lookup.dao(UnidadeVODaoImpl.class);
		
		List<UnidadeVO> listaUnidades = new ArrayList<>();
		
		if (params.get("enteId") != null && params.get("poderId") == null) {
			listaUnidades = dao.byEnte((Integer) params.get("enteId")); 
			
		} else if (params.get("orgaoId") != null) {
			listaUnidades = dao.byOrgao((Integer) params.get("orgaoId"));
			
		} else if (params.get("poderId") != null) {
			listaUnidades = dao.byPoder((Integer) params.get("enteId"), (Integer) params.get("poderId"));
			
		} else if (params.get("unidadeId") != null) {
			listaUnidades.add(dao.byId((Integer) params.get("unidadeId")));
		}
		
		return listaUnidades;
	}
	
	public default List<UnidadeVO> recuperarUnidades(Integer ente, Integer orgao, Integer poder, Integer unidadeGestora) {
		
		List<UnidadeVO> listaIdsUnidades = new ArrayList<>();
		
		UnidadeVODaoImpl dao = Lookup.dao(UnidadeVODaoImpl.class);
		
		if (ente != null && poder == null) {
			listaIdsUnidades = dao.byEnte(ente);
			
		} else if (orgao != null) {
			listaIdsUnidades = dao.byOrgao(orgao);
			
		} else if (poder != null) {
			listaIdsUnidades = dao.byPoder(ente, poder);
			
		} else if(unidadeGestora != null) {
			listaIdsUnidades.add(dao.byId(unidadeGestora));
		}
		
		return listaIdsUnidades;
	}
	
	/**
	 * Retorna uma coleção de ids de uma coleção (em geral utilizado para pesquisas).
	 * 
	 * @param unidades Coleção de unidades a ter seus identificadores extraídos.
	 * @return Retorna uma coleção de ids da coleção de Unidades.
	 */
	public default List<Integer> extrairIds(List<UnidadeVO> unidades) {
		return unidades.stream().map(unidade -> unidade.getId()).collect(Collectors.toList());
	}
	
	public default Date toDate(Object obj) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(((Timestamp) obj).getTime());
		
		return calendar.getTime();
	}
	
	public default BigDecimal toBigDecimal(Object obj) {
		
		if (obj == null)
			return null;
		
		return new BigDecimal(String.valueOf(obj));
	}
}