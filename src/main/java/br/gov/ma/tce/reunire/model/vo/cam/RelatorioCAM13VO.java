package br.gov.ma.tce.reunire.model.vo.cam;

import java.math.BigDecimal;
import java.util.Date;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioCAM13VO extends DemonstrativoVO {
	
	private String cpf;
	private String nome;
	private Date dataInscricao;
	private Date dataConcessao;
	private String tipoBeneficio;
	private BigDecimal provento;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataInscricao() {
		return dataInscricao;
	}

	public void setDataInscricao(Date dataInscricao) {
		this.dataInscricao = dataInscricao;
	}

	public Date getDataConcessao() {
		return dataConcessao;
	}

	public void setDataConcessao(Date dataConcessao) {
		this.dataConcessao = dataConcessao;
	}

	public String getTipoBeneficio() {
		return tipoBeneficio;
	}

	public void setTipoBeneficio(String tipoBeneficio) {
		this.tipoBeneficio = tipoBeneficio;
	}

	public BigDecimal getProvento() {
		return provento;
	}

	public void setProvento(BigDecimal provento) {
		this.provento = provento;
	}
}