package br.gov.ma.tce.reunire.model.vo.cam;

import java.math.BigDecimal;
import java.util.Date;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioCAM22VO extends DemonstrativoVO {
	
	private Integer idOrgao;
	private String descricaoOrgao;
	private String identificacao;
	private String destinacao;
	private BigDecimal valor;
	private String situacao;
	private Date dataAquisicao;
	
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

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public String getDestinacao() {
		return destinacao;
	}

	public void setDestinacao(String destinacao) {
		this.destinacao = destinacao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Date getDataAquisicao() {
		return dataAquisicao;
	}

	public void setDataAquisicao(Date dataAquisicao) {
		this.dataAquisicao = dataAquisicao;
	}

}
