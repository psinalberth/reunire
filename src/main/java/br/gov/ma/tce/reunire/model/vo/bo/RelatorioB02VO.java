package br.gov.ma.tce.reunire.model.vo.bo;

import java.math.BigDecimal;

public class RelatorioB02VO {
	
	private String total;
	private String totalCategoria;
	private String categoria;
	private String grupo;
	private String modalidade;
	private BigDecimal dotacaoInicial;
	private BigDecimal dotacaoAtualizada;
	private BigDecimal despesasEmpenhadas;
	private BigDecimal despesasLiquidadas;
	private BigDecimal despesasPagas;
	
	public String getTotal() {
		return total;
	}
	
	public void setTotal(String total) {
		this.total = total;
	}
	
	public String getTotalCategoria() {
		return totalCategoria;
	}
	
	public void setTotalCategoria(String totalCategoria) {
		this.totalCategoria = totalCategoria;
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public String getGrupo() {
		return grupo;
	}
	
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	
	public String getModalidade() {
		return modalidade;
	}
	
	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}
	
	public BigDecimal getDotacaoInicial() {
		return dotacaoInicial;
	}
	
	public void setDotacaoInicial(BigDecimal dotacaoInicial) {
		this.dotacaoInicial = dotacaoInicial;
	}
	
	public BigDecimal getDotacaoAtualizada() {
		return dotacaoAtualizada;
	}
	
	public void setDotacaoAtualizada(BigDecimal dotacaoAtualizada) {
		this.dotacaoAtualizada = dotacaoAtualizada;
	}
	
	public BigDecimal getDespesasEmpenhadas() {
		return despesasEmpenhadas;
	}
	
	public void setDespesasEmpenhadas(BigDecimal despesasEmpenhadas) {
		this.despesasEmpenhadas = despesasEmpenhadas;
	}
	
	public BigDecimal getDespesasLiquidadas() {
		return despesasLiquidadas;
	}
	
	public void setDespesasLiquidadas(BigDecimal despesasLiquidadas) {
		this.despesasLiquidadas = despesasLiquidadas;
	}
	
	public BigDecimal getDespesasPagas() {
		return despesasPagas;
	}
	
	public void setDespesasPagas(BigDecimal despesasPagas) {
		this.despesasPagas = despesasPagas;
	}
}