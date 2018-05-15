package br.gov.ma.tce.reunire.model.vo.cam;

import java.math.BigDecimal;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioCAM23VO extends DemonstrativoVO {
	
	private Integer idOrgao;
	private String descricaoOrgao;
	private String identificacaoBem;
	private int quantidade;
	private BigDecimal valorUnitario;
	private String setorResponsavel;
	
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

	public String getIdentificacaoBem() {
		return identificacaoBem;
	}

	public void setIdentificacaoBem(String identificacaoBem) {
		this.identificacaoBem = identificacaoBem;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public String getSetorResponsavel() {
		return setorResponsavel;
	}

	public void setSetorResponsavel(String setorResponsavel) {
		this.setorResponsavel = setorResponsavel;
	}
}