package br.gov.ma.tce.reunire.model.vo.cam;

import java.math.BigDecimal;
import java.util.Date;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioCAM20VO extends DemonstrativoVO {
	
	private String deliberacao;
	private BigDecimal valorDebito;
	private String identificacaoDevedor;
	private String providencias;
	private BigDecimal valorRessarcido;
	private Date dataRessarcimento;

	public String getDeliberacao() {
		return deliberacao;
	}

	public void setDeliberacao(String deliberacao) {
		this.deliberacao = deliberacao;
	}

	public BigDecimal getValorDebito() {
		return valorDebito;
	}

	public void setValorDebito(BigDecimal valorDebito) {
		this.valorDebito = valorDebito;
	}

	public String getIdentificacaoDevedor() {
		return identificacaoDevedor;
	}

	public void setIdentificacaoDevedor(String identificacaoDevedor) {
		this.identificacaoDevedor = identificacaoDevedor;
	}

	public String getProvidencias() {
		return providencias;
	}

	public void setProvidencias(String providencias) {
		this.providencias = providencias;
	}

	public BigDecimal getValorRessarcido() {
		return valorRessarcido;
	}

	public void setValorRessarcido(BigDecimal valorRessarcido) {
		this.valorRessarcido = valorRessarcido;
	}

	public Date getDataRessarcimento() {
		return dataRessarcimento;
	}

	public void setDataRessarcimento(Date dataRessarcimento) {
		this.dataRessarcimento = dataRessarcimento;
	}
}