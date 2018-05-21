package br.gov.ma.tce.reunire.viewmodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

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
	private Integer exercicio;
	private Integer modulo;
	private OrgaoVO orgao;
	private UnidadeVO unidade;
	private PoderVO poder;
	private TipoRelatorio tipoRelatorio;
	private ModuloRelatorioPrestacao moduloRelatorio;
	
	private Map<String, Object> params;
	
	private List<TipoRelatorio> listaRelatoriosSelecionados;
	private List<TipoRelatorio> listaRelatorios;
	private List<EnteVO> entes;
	private List<OrgaoVO> orgaos;
	private List<UnidadeVO> unidades;
	private List<PoderVO> poderes;
	private List<ModuloRelatorioPrestacao> modulos;
	
	private boolean formularioVisivel = false;
	private boolean selecaoEnteVisivel = false;
	private boolean selecaoOrgaoVisivel = false;
	private boolean selecaoUnidadeVisivel = false;
	
	final Integer moduloContasGoverno = 1;
	
	@SuppressWarnings("rawtypes")
	ConsultasGestoresImpl daoGestores = Lookup.dao(ConsultasGestoresImpl.class);
	
	@SuppressWarnings("rawtypes")
	ConsultasPrestacaoImpl daoPrestacao = Lookup.dao(ConsultasPrestacaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Init
	@NotifyChange("*")
	public void init() {
		
		if (Executions.getCurrent().getParameter("ente") != null) {
			ente = (EnteVO) daoGestores.byId(EnteVO.class, Integer.valueOf(Executions.getCurrent().getParameter("ente")));
		}
		
		if (Executions.getCurrent().getParameter("unidade") != null) {
			
			unidade = (UnidadeVO) daoGestores.byId(UnidadeVO.class, Integer.valueOf(Executions.getCurrent().getParameter("unidade")));
			ente = unidade.getOrgao().getEnte();
		}
		
		if (Executions.getCurrent().getParameter("modulo") != null) {
			modulo = Integer.valueOf(Executions.getCurrent().getParameter("modulo"));
		}
		
		if (ente != null || unidade != null) {
			formularioVisivel = true;
		}
		
		exercicio = new Integer(2017);
		listaRelatorios = ((TipoRelatorioDao) Lookup.dao(TipoRelatorioDaoImpl.class)).findAllByModulo(modulo != null ? modulo : moduloContasGoverno);
		entes = daoGestores.findAll(EnteVO.class);
		poderes = daoGestores.findAll(PoderVO.class);
		modulos = daoPrestacao.findAll(ModuloRelatorioPrestacao.class);
	}
	
	@Command
	@NotifyChange("listaRelatorios")
	public void pesquisar() {
		
		listaRelatorios = ((TipoRelatorioDao) Lookup.dao(TipoRelatorioDaoImpl.class)).findAllByModulo(modulo != null ? modulo : moduloContasGoverno);
		
		listaRelatorios = ((TipoRelatorioDao) Lookup.dao(TipoRelatorioDaoImpl.class)).findAll();
		
		if (filtroRelatorio != null && filtroRelatorio.trim().length() > 0) {
			
			listaRelatorios = listaRelatorios.stream().filter(relatorio -> {
				return relatorio.getDescricao().toUpperCase().contains(filtroRelatorio.toUpperCase()) ||
					   relatorio.getCodigo().toUpperCase().contains(filtroRelatorio.toUpperCase());
			}).collect(Collectors.toList());
		}
	}
	
	// Métodos relacionados ao lookup e seleção de Entes
	
	@SuppressWarnings("unchecked")
	@Command
	@NotifyChange("entes")
	public void filtrarEnte(@BindingParam("filtro") String filtro) {
		
		entes = daoGestores.findAll(EnteVO.class);
		
		if (filtro != null && filtro.trim().length() > 0) {
			entes = entes.stream().filter(ente -> ente.getNome().toUpperCase().contains(filtro.toUpperCase())).collect(Collectors.toList());
		}
	}
	
	@Command
	@NotifyChange("selecaoEnteVisivel")
	public void abrirEnte() {
		selecaoEnteVisivel = true;
	}
	
	@Command
	@NotifyChange("selecaoEnteVisivel")
	public void fecharEnte() {
		selecaoEnteVisivel = false;
	}
	
	// Métodos relacionados ao lookup e seleção de Órgãos
	
	@SuppressWarnings("unchecked")
	@Command
	@NotifyChange({"orgaos", "orgao", "unidade", "unidades", "selecaoEnteVisivel"})
	public void preencherOrgaos() {
		
		selecaoEnteVisivel = false;
		
		if (ente != null) {
			
			orgaos = daoGestores.findAllByEnte(OrgaoVO.class, ente.getId());
			orgao = null;
			unidade = null;
			
			preencherUnidades();
		}
	}
	
	@Command
	@NotifyChange("orgaos")
	public void filtrarOrgao(@BindingParam("filtro") String filtro) {
		
		preencherOrgaos();
		
		if (filtro != null && filtro.trim().length() > 0) {
			orgaos = orgaos.stream().filter(orgao -> orgao.getNome().toUpperCase().contains(filtro.toUpperCase())).collect(Collectors.toList());
		}
	}
	
	@Command
	@NotifyChange("selecaoOrgaoVisivel")
	public void abrirOrgao() {
		selecaoOrgaoVisivel = true;
	}
	
	@Command
	@NotifyChange("selecaoOrgaoVisivel")
	public void fecharOrgao() {
		selecaoOrgaoVisivel = false;
	}
	
	// Métodos relacionados ao lookup e seleção de Unidades Gestoras
	
	@SuppressWarnings("unchecked")
	@Command
	@NotifyChange({"unidades", "unidade", "selecaoOrgaoVisivel"})
	public void preencherUnidades() {
		
		selecaoOrgaoVisivel = false;
		
		if (orgao != null) {
			
			unidades = daoGestores.findAllByOrgao(UnidadeVO.class, orgao.getId());
			unidade = null;
			
		} else if (ente != null) {
			
			UnidadeVODaoImpl daoUnidade = Lookup.dao(UnidadeVODaoImpl.class);
			unidades = daoUnidade.byEnte(ente.getId());
		}
	}
	
	@Command
	@NotifyChange("unidades")
	public void filtrarUnidade(@BindingParam("filtro") String filtro) {
		
		preencherUnidades();
		
		if (filtro != null && filtro.trim().length() > 0) {
			unidades = unidades.stream().filter(unidade -> unidade.getNome().toUpperCase().contains(filtro.toUpperCase())).collect(Collectors.toList());
		}
	}
	
	@Command
	@NotifyChange("selecaoUnidadeVisivel")
	public void abrirUnidade() {
		selecaoUnidadeVisivel = true;
	}
	
	@Command
	@NotifyChange("selecaoUnidadeVisivel")
	public void fecharUnidade() {
		selecaoUnidadeVisivel = false;
	}
	
	@Command
	@NotifyChange("formularioVisivel")
	public void irParaRelatorio() {
		
		if (ente == null) {
			
			Messagebox.show("Para prosseguir, a seleção do ente é obrigatória.", "Operação Não Permitida", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		
		params = new HashMap<String, Object>();	
		
		params.put("ente", ente.getId());
		params.put("exercicio", exercicio);
		params.put("modulo", null);
		
		formularioVisivel = true;
	}
	
	@Command
	public void imprimirRelatorio(@BindingParam("tipoRelatorio") String tipoRelatorio) {
		
		String url =  unidade != null ? "/report.zul?unidade=" + unidade.getId() : "/report.zul?ente=" + ente.getId();
		
		if (listaRelatoriosSelecionados != null && listaRelatoriosSelecionados.size() > 0) {
			
			String tiposRelatorio = "";
			
			for (int i = 0; i < listaRelatoriosSelecionados.size(); i++) {
				
				tiposRelatorio += listaRelatoriosSelecionados.get(i).getCodigo();
				
				if ((i+1) < listaRelatoriosSelecionados.size())
					tiposRelatorio += ",";
			}
			
			url = url + "&tipoRelatorio=" + tiposRelatorio.toLowerCase() + "&formato=PDF";
			
		} else if (tipoRelatorio != null && tipoRelatorio.trim().length()> 0) {
			url = url + "&tipoRelatorio=" + tipoRelatorio.toLowerCase() + "&formato=PDF";
		}
		
		if (modulo != null) {
			url = url + "&modulo=" + modulo;
		}
		
		Executions.sendRedirect(url);
	}
	
	@Command
	@NotifyChange({"formularioVisivel", "tipoRelatorio", "ente", "orgao", "unidade", "poder", "modulo"})
	public void voltar() {
		
		formularioVisivel = false;
		
		tipoRelatorio = null;
		
		ente = null;
		orgao = null;
		unidade = null;
		poder = null;
		modulo = null;
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
	
	public Integer getExercicio() {
		return exercicio;
	}
	
	public void setExercicio(Integer exercicio) {
		this.exercicio = exercicio;
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
	
	public void setSelecaoEnteVisivel(boolean selecaoEnteVisivel) {
		this.selecaoEnteVisivel = selecaoEnteVisivel;
	}
	
	public boolean isSelecaoEnteVisivel() {
		return selecaoEnteVisivel;
	}
	
	public void setSelecaoOrgaoVisivel(boolean selecaoOrgaoVisivel) {
		this.selecaoOrgaoVisivel = selecaoOrgaoVisivel;
	}
	
	public boolean isSelecaoOrgaoVisivel() {
		return selecaoOrgaoVisivel;
	}
	
	public void setSelecaoUnidadeVisivel(boolean selecaoUnidadeVisivel) {
		this.selecaoUnidadeVisivel = selecaoUnidadeVisivel;
	}
	
	public boolean isSelecaoUnidadeVisivel() {
		return selecaoUnidadeVisivel;
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
	
	public Integer getModulo() {
		return modulo;
	}
	
	public void setModulo(Integer modulo) {
		this.modulo = modulo;
	}
	
	public ModuloRelatorioPrestacao getModuloRelatorio() {
		return moduloRelatorio;
	}
	
	public void setModuloRelatorio(ModuloRelatorioPrestacao moduloRelatorio) {
		this.moduloRelatorio = moduloRelatorio;
	}
	
	public Map<String, Object> getParams() {
		return params;
	}
	
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}