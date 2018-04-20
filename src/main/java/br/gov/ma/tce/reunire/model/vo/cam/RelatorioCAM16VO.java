package br.gov.ma.tce.reunire.model.vo.cam;

import java.math.BigDecimal;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioCAM16VO extends DemonstrativoVO {
	
	private String numeroProcesso;
	private String anoProcesso;
	private String dataDoFato;
	private String dataInstauracao;
	private String cpf1;
	private String cpf2;
	private String cpf3;
	private String cpf4;
	private String cpf5;
	private String cpf6;
	private String cpf7;
	private String cpf8;
	private BigDecimal valorDoDano;
	private String situacao;
	
	public String getNumeroProcesso() {
		return numeroProcesso;
	}
	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}
	
	public String getAnoProcesso() {
		return anoProcesso;
	}
	public void setAnoProcesso(String anoProcesso) {
		this.anoProcesso = anoProcesso;
	}
	
	public String getDataDoFato() {
		return dataDoFato;
	}
	public void setDataDoFato(String dataDoFato) {
		this.dataDoFato = dataDoFato;
	}
	
	public String getDataInstauracao() {
		return dataInstauracao;
	}
	public void setDataInstauracao(String dataInstauracao) {
		this.dataInstauracao = dataInstauracao;
	}
	
	public String getCpf1() {
		return cpf1;
	}
	public void setCpf1(String cpf1) {
		this.cpf1 = cpf1;
	}
	public String getCpf2() {
		return cpf2;
	}
	public void setCpf2(String cpf2) {
		this.cpf2 = cpf2;
	}
	public String getCpf3() {
		return cpf3;
	}
	public void setCpf3(String cpf3) {
		this.cpf3 = cpf3;
	}
	public String getCpf4() {
		return cpf4;
	}
	public void setCpf4(String cpf4) {
		this.cpf4 = cpf4;
	}
	public String getCpf5() {
		return cpf5;
	}
	public void setCpf5(String cpf5) {
		this.cpf5 = cpf5;
	}
	public String getCpf6() {
		return cpf6;
	}
	public void setCpf6(String cpf6) {
		this.cpf6 = cpf6;
	}
	public String getCpf7() {
		return cpf7;
	}
	public void setCpf7(String cpf7) {
		this.cpf7 = cpf7;
	}
	public String getCpf8() {
		return cpf8;
	}
	public void setCpf8(String cpf8) {
		this.cpf8 = cpf8;
	}
	public BigDecimal getValorDoDano() {
		return valorDoDano;
	}
	public void setValorDoDano(BigDecimal valorDoDano) {
		this.valorDoDano = valorDoDano;
	}
	
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
}
