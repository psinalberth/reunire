package br.gov.ma.tce.reunire.model.vo.cam;

import java.math.BigDecimal;
import java.util.Date;

public class RelatorioCAM03VO {

	private Integer idUnidade;
	private String descricaoUnidade;
	private Date dataAto;
	private String tipoAto;
	private BigDecimal valor;
	private BigDecimal dotacaoInicial;

	public Integer getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(Integer idUnidade) {
		this.idUnidade = idUnidade;
	}
	
	public String getDescricaoUnidade() {
		return descricaoUnidade;
	}
	
	public void setDescricaoUnidade(String descricaoUnidade) {
		this.descricaoUnidade = descricaoUnidade;
	}

	public Date getDataAto() {
		return dataAto;
	}

	public void setDataAto(Date dataAto) {
		this.dataAto = dataAto;
	}

	public String getTipoAto() {
		return tipoAto;
	}

	public void setTipoAto(String tipoAto) {
		this.tipoAto = tipoAto;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getDotacaoInicial() {
		return dotacaoInicial;
	}

	public void setDotacaoInicial(BigDecimal dotacaoInicial) {
		this.dotacaoInicial = dotacaoInicial;
	}

	

}
