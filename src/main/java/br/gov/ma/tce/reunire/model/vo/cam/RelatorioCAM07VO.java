package br.gov.ma.tce.reunire.model.vo.cam;

import java.math.BigDecimal;
import java.util.Date;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioCAM07VO extends DemonstrativoVO {
	
	private String credor;
	private Date dataVencimento;
	private BigDecimal valorContrato;
	private BigDecimal parcelasResgatadas;

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