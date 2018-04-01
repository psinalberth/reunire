package br.gov.ma.tce.reunire.viewmodel;

import java.util.List;
import java.util.stream.Collectors;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import br.gov.ma.tce.reunire.dao.TipoRelatorioDao;
import br.gov.ma.tce.reunire.dao.impl.TipoRelatorioDaoImpl;
import br.gov.ma.tce.reunire.dao.impl.gestor.UnidadeVODaoImpl;
import br.gov.ma.tce.reunire.model.TipoRelatorio;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;
import br.gov.ma.tce.reunire.util.Lookup;

public class IndexViewModel {
	
	private String filtroRelatorio;
	private List<TipoRelatorio> listaRelatoriosSelecionados;
	private List<TipoRelatorio> listaRelatorios;
	
	@Init
	@NotifyChange("*")
	public void init() {
		 listaRelatorios = ((TipoRelatorioDao) Lookup.dao(TipoRelatorioDaoImpl.class)).findAll();
		 
		 List<UnidadeVO> lista = Lookup.dao(UnidadeVODaoImpl.class).byEnte(999999);
		 
		 System.out.println(lista.size());
		 
	}
	
	@Command
	@NotifyChange("listaRelatorios")
	public void pesquisar() {
		
		listaRelatorios = ((TipoRelatorioDao) Lookup.dao(TipoRelatorioDaoImpl.class)).findAll();
		
		if (filtroRelatorio != null && filtroRelatorio.trim().length() > 0) {
			
			listaRelatorios = listaRelatorios.stream().filter(relatorio -> {
				return relatorio.getDescricao().toUpperCase().contains(filtroRelatorio.toUpperCase()) ||
					   relatorio.getCodigo().toUpperCase().contains(filtroRelatorio.toUpperCase());
			}).collect(Collectors.toList());
		}
	}
	
	@Command
	public void imprimirRelatorio(@BindingParam("tipoRelatorio") TipoRelatorio tipoRelatorio) {
		
	}
	
	@Command
	public void imprimirRelatorioEmLote() {
		
	}
	
	public String getFiltroRelatorio() {
		return filtroRelatorio;
	}
	
	public void setFiltroRelatorio(String filtroRelatorio) {
		this.filtroRelatorio = filtroRelatorio;
	}
	
	public List<TipoRelatorio> getListaRelatorios() {
		return listaRelatorios;
	}
	
	public void setListaRelatorios(List<TipoRelatorio> listaRelatorios) {
		this.listaRelatorios = listaRelatorios;
	}

	public List<TipoRelatorio> getListaRelatoriosSelecionados() {
		return listaRelatoriosSelecionados;
	}

	public void setListaRelatoriosSelecionados(List<TipoRelatorio> listaRelatoriosSelecionados) {
		this.listaRelatoriosSelecionados = listaRelatoriosSelecionados;
	}
}