package br.gov.ma.tce.reunire.model.vo.cam;

import java.math.BigDecimal;
import java.util.Date;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioCAM25VO extends DemonstrativoVO {
	
	private Integer idOrgao;
	private String descricaoOrgao;
	private String numeroEmpenho;
	private String modalidade;
	private Date dataContabil;
	private String naturezaDespesa;
	private String credor;
	private BigDecimal valorEmpenhado;
	private BigDecimal valorReforcado;
	private BigDecimal valorAnulado;
	private BigDecimal valorLiquidado;
	private BigDecimal valorPago;
	private String documento;
	private BigDecimal saldo;
	
	public Integer getIdOrgao() {
		return idOrgao;
	}
	
	public void setIdOrgao(Integer idOrgao) {
		this.idOrgao = idOrgao;
	}
	
	public String getDescricaoOrgao() {
		return descricaoOrgao;
	}
	
	public void setDescricaoOrgao(String descricaoOrgao) {
		this.descricaoOrgao = descricaoOrgao;
	}

	public String getNumeroEmpenho() {
		return numeroEmpenho;
	}

	public void setNumeroEmpenho(String numeroEmpenho) {
		this.numeroEmpenho = numeroEmpenho;
	}

	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}

	public Date getDataContabil() {
		return dataContabil;
	}

	public void setDataContabil(Date dataContabil) {
		this.dataContabil = dataContabil;
	}

	public String getNaturezaDespesa() {
		return naturezaDespesa;
	}

	public void setNaturezaDespesa(String naturezaDespesa) {
		this.naturezaDespesa = naturezaDespesa;
	}

	public String getCredor() {
		return credor;
	}

	public void setCredor(String credor) {
		this.credor = credor;
	}

	public BigDecimal getValorEmpenhado() {
		return valorEmpenhado;
	}

	public void setValorEmpenhado(BigDecimal valorEmpenhado) {
		this.valorEmpenhado = valorEmpenhado;
	}

	public BigDecimal getValorReforcado() {
		return valorReforcado;
	}

	public void setValorReforcado(BigDecimal valorReforcado) {
		this.valorReforcado = valorReforcado;
	}

	public BigDecimal getValorAnulado() {
		return valorAnulado;
	}

	public void setValorAnulado(BigDecimal valorAnulado) {
		this.valorAnulado = valorAnulado;
	}

	public BigDecimal getValorLiquidado() {
		return valorLiquidado;
	}

	public void setValorLiquidado(BigDecimal valorLiquidado) {
		this.valorLiquidado = valorLiquidado;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}
	
	public String getDocumento() {
		return documento;
	}
	
	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
}
