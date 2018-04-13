package br.gov.ma.tce.reunire.viewmodel;

import java.util.List;
import java.util.stream.Collectors;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import br.gov.ma.tce.reunire.dao.TipoRelatorioDao;
import br.gov.ma.tce.reunire.dao.impl.TipoRelatorioDaoImpl;
import br.gov.ma.tce.reunire.dao.impl.gestor.ConsultasGestoresImpl;
import br.gov.ma.tce.reunire.dao.impl.gestor.UnidadeVODaoImpl;
import br.gov.ma.tce.reunire.dao.impl.prestacao.ConsultasPrestacaoImpl;
import br.gov.ma.tce.reunire.model.ModuloRelatorioPrestacao;
import br.gov.ma.tce.reunire.model.TipoRelatorio;
import br.gov.ma.tce.reunire.model.vo.gestor.EnteVO;
import br.gov.ma.tce.reunire.model.vo.gestor.OrgaoVO;
import br.gov.ma.tce.reunire.model.vo.gestor.PoderVO;
import br.gov.ma.tce.reunire.model.vo.gestor.UnidadeVO;
import br.gov.ma.tce.reunire.util.Lookup;

public class IndexViewModel {
	
	private String filtroRelatorio;
	
	private EnteVO ente;
	private OrgaoVO orgao;
	private UnidadeVO unidade;
	private PoderVO poder;
	private TipoRelatorio tipoRelatorio;
	private ModuloRelatorioPrestacao modulo;
	
	private List<TipoRelatorio> listaRelatoriosSelecionados;
	private List<TipoRelatorio> listaRelatorios;
	private List<EnteVO> entes;
	private List<OrgaoVO> orgaos;
	private List<UnidadeVO> unidades;
	private List<PoderVO> poderes;
	private List<ModuloRelatorioPrestacao> modulos;
	
	private boolean formularioVisivel = false;
	
	@SuppressWarnings("rawtypes")
	ConsultasGestoresImpl daoGestores = Lookup.dao(ConsultasGestoresImpl.class);
	
	@SuppressWarnings("rawtypes")
	ConsultasPrestacaoImpl daoPrestacao = Lookup.dao(ConsultasPrestacaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Init
	@NotifyChange("*")
	public void init() {
		 
		 listaRelatorios = ((TipoRelatorioDao) Lookup.dao(TipoRelatorioDaoImpl.class)).findAll();
		 entes = daoGestores.findAll(EnteVO.class);
		 poderes = daoGestores.findAll(PoderVO.class);
		 modulos = daoPrestacao.findAll(ModuloRelatorioPrestacao.class);
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
	
	@SuppressWarnings("unchecked")
	@Command
	@NotifyChange({"orgaos", "orgao", "unidade", "unidades"})
	public void preencherOrgaos() {
		
		if (ente != null) {
			
			orgaos = daoGestores.findAllByEnte(OrgaoVO.class, ente.getId());
			orgao = null;
			unidade = null;
			
			preencherUnidades();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Command
	@NotifyChange({"unidades", "unidade"})
	public void preencherUnidades() {
		
		if (orgao != null) {
			
			unidades = daoGestores.findAllByOrgao(UnidadeVO.class, orgao.getId());
			unidade = null;
			
		} else if (ente != null) {
			
			UnidadeVODaoImpl daoUnidade = Lookup.dao(UnidadeVODaoImpl.class);
			unidades = daoUnidade.byEnte(ente.getId());
		}
	}
	
	@Command
	@NotifyChange({"formularioVisivel", "tipoRelatorio"})
	public void imprimirRelatorio(@BindingParam("tipoRelatorio") TipoRelatorio tipoRelatorio) {
		
		formularioVisivel = true;
		this.tipoRelatorio = tipoRelatorio;
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

	public EnteVO getEnte() {
		return ente;
	}

	public void setEnte(EnteVO ente) {
		this.ente = ente;
	}

	public OrgaoVO getOrgao() {
		return orgao;
	}

	public void setOrgao(OrgaoVO orgao) {
		this.orgao = orgao;
	}

	public UnidadeVO getUnidade() {
		return unidade;
	}

	public void setUnidade(UnidadeVO unidade) {
		this.unidade = unidade;
	}

	public PoderVO getPoder() {
		return poder;
	}

	public void setPoder(PoderVO poder) {
		this.poder = poder;
	}

	public List<EnteVO> getEntes() {
		return entes;
	}

	public void setEntes(List<EnteVO> entes) {
		this.entes = entes;
	}

	public List<OrgaoVO> getOrgaos() {
		return orgaos;
	}

	public void setOrgaos(List<OrgaoVO> orgaos) {
		this.orgaos = orgaos;
	}

	public List<UnidadeVO> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<UnidadeVO> unidades) {
		this.unidades = unidades;
	}

	public List<PoderVO> getPoderes() {
		return poderes;
	}

	public void setPoderes(List<PoderVO> poderes) {
		this.poderes = poderes;
	}
	
	public List<ModuloRelatorioPrestacao> getModulos() {
		return modulos;
	}
	
	public void setModulos(List<ModuloRelatorioPrestacao> modulos) {
		this.modulos = modulos;
	}
	
	public void setFormularioVisivel(boolean formularioVisivel) {
		this.formularioVisivel = formularioVisivel;
	}
	
	public boolean isFormularioVisivel() {
		return formularioVisivel;
	}
	
	public TipoRelatorio getTipoRelatorio() {
		return tipoRelatorio;
	}
	
	public void setTipoRelatorio(TipoRelatorio tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}
	
	public ModuloRelatorioPrestacao getModulo() {
		return modulo;
	}
	
	public void setModulo(ModuloRelatorioPrestacao modulo) {
		this.modulo = modulo;
	}
}