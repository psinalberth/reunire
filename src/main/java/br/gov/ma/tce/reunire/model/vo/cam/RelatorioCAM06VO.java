package br.gov.ma.tce.reunire.model.vo.cam;

import java.math.BigDecimal;
import java.util.Date;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioCAM06VO extends DemonstrativoVO {
	
	private String numeroProcesso;
	private String cpfSuprido;
	private String finalidadeAdiantamento;
	private Date dataRecebimento;
	private BigDecimal valor;
	private Integer prazoAplicacao;
	private Date dataPrestacaoContas;
	private String situacao;

	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public String getCpfSuprido() {
		return cpfSuprido;
	}

	public void setCpfSuprido(String cpfSuprido) {
		this.cpfSuprido = cpfSuprido;
	}

	public String getFinalidadeAdiantamento() {
		return finalidadeAdiantamento;
	}

	public void setFinalidadeAdiantamento(String finalidadeAdiantamento) {
		this.finalidadeAdiantamento = finalidadeAdiantamento;
	}

	public Date getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(Date dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Integer getPrazoAplicacao() {
		return prazoAplicacao;
	}

	public void setPrazoAplicacao(Integer prazoAplicacao) {
		this.prazoAplicacao = prazoAplicacao;
	}

	public Date getDataPrestacaoContas() {
		return dataPrestacaoContas;
	}

	public void setDataPrestacaoContas(Date dataPrestacaoContas) {
		this.dataPrestacaoContas = dataPrestacaoContas;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
}
