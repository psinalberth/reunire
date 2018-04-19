package br.gov.ma.tce.reunire.model.vo.cam;

import java.math.BigDecimal;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioCAM15VO extends DemonstrativoVO {

	private String numeroOficio;
	private String natureza;
	private String cpf_cnpj_credor;
	private BigDecimal valorInscrito;
	private BigDecimal valorPago;

	public String getNumeroOficio() {
		return numeroOficio;
	}

	public void setNumeroOficio(String numeroOficio) {
		this.numeroOficio = numeroOficio;
	}

	public String getNatureza() {
		return natureza;
	}

	public void setNatureza(String natureza) {
		this.natureza = natureza;
	}

	public String getCpf_cnpj_credor() {
		return cpf_cnpj_credor;
	}

	public void setCpf_cnpj_credor(String cpf_cnpj_credor) {
		this.cpf_cnpj_credor = cpf_cnpj_credor;
	}

	public BigDecimal getValorInscrito() {
		return valorInscrito;
	}

	public void setValorInscrito(BigDecimal valorInscrito) {
		this.valorInscrito = valorInscrito;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

}
