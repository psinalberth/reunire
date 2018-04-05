package br.gov.ma.tce.reunire.model.vo.d;

import java.math.BigDecimal;

public class RelatorioD011AVO {
	
	private Integer idUnidade;
	private String descricaoUnidade;
	private String descricaoNaturezaDespesa;
	private String codigoFuncao;
	private String codigoSubFuncao;
	private String codigoPrograma;
	private String codigoAcao;
	private String descricaoAcao;
	private BigDecimal valorCreditoOrcamentario;
	private BigDecimal valorCreditoEspecial;
	private BigDecimal valorDespesaRealizada;
	
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

	public String getDescricaoNaturezaDespesa() {
		return descricaoNaturezaDespesa;
	}

	public void setDescricaoNaturezaDespesa(String decricaoNaturezaDespesa) {
		this.descricaoNaturezaDespesa = decricaoNaturezaDespesa;
	}
	
	public String getCodigoFuncao() {
		return codigoFuncao;
	}

	public void setCodigoFuncao(String codigoFuncao) {
		this.codigoFuncao = codigoFuncao;
	}

	public String getCodigoSubFuncao() {
		return codigoSubFuncao;
	}

	public void setCodigoSubFuncao(String codigoSubFuncao) {
		this.codigoSubFuncao = codigoSubFuncao;
	}

	public String getCodigoPrograma() {
		return codigoPrograma;
	}

	public void setCodigoPrograma(String codigoPrograma) {
		this.codigoPrograma = codigoPrograma;
	}

	public String getCodigoAcao() {
		return codigoAcao;
	}

	public void setCodigoAcao(String codigoAcao) {
		this.codigoAcao = codigoAcao;
	}

	public String getDescricaoAcao() {
		return descricaoAcao;
	}

	public void setDescricaoAcao(String descricaoAcao) {
		this.descricaoAcao = descricaoAcao;
	}

	public BigDecimal getValorCreditoEspecial() {
		return valorCreditoEspecial;
	}

	public void setValorCreditoEspecial(BigDecimal valorCreditoEspecial) {
		this.valorCreditoEspecial = valorCreditoEspecial;
	}

	public BigDecimal getValorCreditoOrcamentario() {
		return valorCreditoOrcamentario;
	}

	public void setValorCreditoOrcamentario(BigDecimal valorCreditoOrcamentario) {
		this.valorCreditoOrcamentario = valorCreditoOrcamentario;
	}

	public BigDecimal getValorDespesaRealizada() {
		return valorDespesaRealizada;
	}

	public void setValorDespesaRealizada(BigDecimal valorDespesaRealizada) {
		this.valorDespesaRealizada = valorDespesaRealizada;
	}

	

}
