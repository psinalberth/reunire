package br.gov.ma.tce.reunire.model.vo.d;

import java.math.BigDecimal;

public class RelatorioD016AVO {
	
	private Integer idUnidade;
	private String descricaoUnidade;
	private String titulo;
	private String leis;
	private String dataDaLei;
	private BigDecimal saldoAnterior;
	private BigDecimal movimentoEmissao;
	private BigDecimal movimentoPagamento;
	private BigDecimal saldoSeguinte;
	private BigDecimal quantidade;
	private BigDecimal valorEmissao;
	private BigDecimal cancelamento;
	
	
	public Integer getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(Integer idUnidade) {
		this.idUnidade = idUnidade;
	}

	public String getDescricaoUnidade() {
		return descricaoUnidade;
	}

	public void setDescricaoUnidade(String descricaoUnidade) {
		this.descricaoUnidade = descricaoUnidade;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getLeis() {
		return leis;
	}

	public void setLeis(String leis) {
		this.leis = leis;
	}

	public String getDataDaLei() {
		return dataDaLei;
	}

	public void setDataDaLei(String dataDaLei) {
		this.dataDaLei = dataDaLei;
	}

	public BigDecimal getSaldoAnterior() {
		return saldoAnterior;
	}

	public void setSaldoAnterior(BigDecimal saldoAnterior) {
		this.saldoAnterior = saldoAnterior;
	}

	public BigDecimal getMovimentoEmissao() {
		return movimentoEmissao;
	}

	public void setMovimentoEmissao(BigDecimal movimentoEmissao) {
		this.movimentoEmissao = movimentoEmissao;
	}

	public BigDecimal getMovimentoPagamento() {
		return movimentoPagamento;
	}

	public void setMovimentoPagamento(BigDecimal movimentoPagamento) {
		this.movimentoPagamento = movimentoPagamento;
	}

	public BigDecimal getSaldoSeguinte() {
		return saldoSeguinte;
	}

	public void setSaldoSeguinte(BigDecimal saldoSeguinte) {
		this.saldoSeguinte = saldoSeguinte;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorEmissao() {
		return valorEmissao;
	}

	public void setValorEmissao(BigDecimal valorEmissao) {
		this.valorEmissao = valorEmissao;
	}

	public BigDecimal getCancelamento() {
		return cancelamento;
	}

	public void setCancelamento(BigDecimal cancelamento) {
		this.cancelamento = cancelamento;
	}

}
