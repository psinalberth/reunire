package br.gov.ma.tce.reunire.model.vo.cam;

import java.math.BigDecimal;
import java.util.Date;

public class RelatorioCAM07VO {
	
	private int idUnidade;
	private String descricaoUnidade;
	private String credor;
	private Date dataVencimento;
	private BigDecimal valorContrato;
	
	private BigDecimal parcelasResgatadas;

	public int getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(int idUnidade) {
		this.idUnidade = idUnidade;
	}

	public String getDescricaoUnidade() {
		return descricaoUnidade;
	}

	public void setDescricaoUnidade(String descricaoUnidade) {
		this.descricaoUnidade = descricaoUnidade;
	}

	public String getCredor() {
		return credor;
	}

	public void setCredor(String credor) {
		this.credor = credor;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public BigDecimal getValorContrato() {
		return valorContrato;
	}

	public void setValorContrato(BigDecimal valorContrato) {
		this.valorContrato = valorContrato;
	}

	public BigDecimal getParcelasResgatadas() {
		return parcelasResgatadas;
	}

	public void setParcelasResgatadas(BigDecimal parcelasResgatadas) {
		this.parcelasResgatadas = parcelasResgatadas;
	}
}