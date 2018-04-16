package br.gov.ma.tce.reunire.model.vo.cam;

import java.math.BigDecimal;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioCAM02VO extends DemonstrativoVO {
	
	private String concedente;
	private String convenente;
	private String dataCelebracao;
	private BigDecimal valor;
	private String objeto;
	private String inicioPrazo;
	private String fimPrazo;
	private String situacao;
	
	public String getCodigoNaturezaReceita() {
		return concedente;
	}
	
	public void setCodigoNaturezaReceita(String codigoNaturezaReceita) {
		this.concedente = codigoNaturezaReceita;
	}
	
	public String getDescricao() {
		return convenente;
	}
	
	public void setDescricao(String descricao) {
		this.convenente = descricao;
	}
	
	public String getConcedente() {
		return concedente;
	}

	public void setConcedente(String concedente) {
		this.concedente = concedente;
	}

	public String getConvenente() {
		return convenente;
	}

	public void setConvenente(String convenente) {
		this.convenente = convenente;
	}

	public String getDataCelebracao() {
		return dataCelebracao;
	}

	public void setDataCelebracao(String dataCelebracao) {
		this.dataCelebracao = dataCelebracao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getObjeto() {
		return objeto;
	}

	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}

	public String getInicioPrazo() {
		return inicioPrazo;
	}

	public void setInicioPrazo(String inicioPrazo) {
		this.inicioPrazo = inicioPrazo;
	}

	public String getFimPrazo() {
		return fimPrazo;
	}

	public void setFimPrazo(String fimPrazo) {
		this.fimPrazo = fimPrazo;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
}
