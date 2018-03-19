package br.gov.ma.tce.reunire.service;

public class RelatorioBuilder {
	
	private Integer tipoRelatorio;
	private Integer ente;
	private Integer orgao;
	private Integer unidadeGestora;
	private Integer exercicio;
	private String formato;
	
	public static RelatorioBuilder criar() {
		return new RelatorioBuilder();
	}

	public RelatorioBuilder tipoRelatorio(Integer tipoRelatorio) {
		
		this.tipoRelatorio = tipoRelatorio;
		return this;
	}
	
	public RelatorioBuilder ente(Integer ente) {
		
		this.ente = ente;
		return this;
	}
	
	public RelatorioBuilder orgao(Integer orgao) {
		
		this.orgao = orgao;
		return this;
	}
	
	public RelatorioBuilder unidadeGestora(Integer unidadeGestora) {
		
		this.unidadeGestora = unidadeGestora;
		return this;
	}
	
	public RelatorioBuilder formato(String formato) {
		
		this.formato = formato;
		return this;
	}
	
	public RelatorioBuilder exercicio(Integer exercicio) {
		
		this.exercicio = exercicio;
		return this;
	}
	
	public RelatorioService getService() {
		return null;
	}
	
	public String build() {
		return "";
	}
	
	public Integer getTipoRelatorio() {
		return tipoRelatorio;
	}
	
	public Integer getEnte() {
		return ente;
	}
	
	public Integer getOrgao() {
		return orgao;
	}
	
	public Integer getUnidadeGestora() {
		return unidadeGestora;
	}
	
	public Integer getExercicio() {
		return exercicio;
	}
	
	public String getFormato() {
		return formato;
	}
}