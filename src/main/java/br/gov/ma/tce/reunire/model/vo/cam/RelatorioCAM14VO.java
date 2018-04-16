package br.gov.ma.tce.reunire.model.vo.cam;

import java.math.BigDecimal;
import java.util.Date;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioCAM14VO extends DemonstrativoVO {
	
	private String identificacaoContratado;
	private String nomeContratado;
	private String identificacaoContratante;
	private String nomeContratante;
	private Date dataContratacao;
	private BigDecimal valorContratacao;
	private BigDecimal valorPago;

	public String getIdentificacaoContratado() {
		return identificacaoContratado;
	}

	public void setIdentificacaoContratado(String identificacaoContratado) {
		this.identificacaoContratado = identificacaoContratado;
	}

	public String getNomeContratado() {
		return nomeContratado;
	}

	public void setNomeContratado(String nomeContratado) {
		this.nomeContratado = nomeContratado;
	}

	public String getIdentificacaoContratante() {
		return identificacaoContratante;
	}

	public void setIdentificacaoContratante(String identificacaoContratante) {
		this.identificacaoContratante = identificacaoContratante;
	}

	public String getNomeContratante() {
		return nomeContratante;
	}

	public void setNomeContratante(String nomeContratante) {
		this.nomeContratante = nomeContratante;
	}

	public Date getDataContratacao() {
		return dataContratacao;
	}

	public void setDataContratacao(Date dataContratacao) {
		this.dataContratacao = dataContratacao;
	}

	public BigDecimal getValorContratacao() {
		return valorContratacao;
	}

	public void setValorContratacao(BigDecimal valorContratacao) {
		this.valorContratacao = valorContratacao;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}
}