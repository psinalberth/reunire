package br.gov.ma.tce.reunire.model.vo.cam;

import java.math.BigDecimal;
import java.util.Date;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioCAM24VO extends DemonstrativoVO {

	private Date competencia;
	private Integer unidadeOrcamentaria;
	private String folhaPagamento;
	private BigDecimal cota_empregado;
	private BigDecimal cota_empregador;
	private String regime;
	private String documento;

	public Date getCompetencia() {
		return competencia;
	}

	public void setCompetencia(Date competencia) {
		this.competencia = competencia;
	}

	public Integer getUnidadeOrcamentaria() {
		return unidadeOrcamentaria;
	}

	public void setUnidadeOrcamentaria(Integer unidadeOrcamentaria) {
		this.unidadeOrcamentaria = unidadeOrcamentaria;
	}

	public String getFolhaPagamento() {
		return folhaPagamento;
	}

	public void setFolhaPagamento(String folhaPagamento) {
		this.folhaPagamento = folhaPagamento;
	}

	public BigDecimal getCota_empregado() {
		return cota_empregado;
	}

	public void setCota_empregado(BigDecimal cota_empregado) {
		this.cota_empregado = cota_empregado;
	}

	public BigDecimal getCota_empregador() {
		return cota_empregador;
	}

	public void setCota_empregador(BigDecimal cota_empregador) {
		this.cota_empregador = cota_empregador;
	}

	public String getRegime() {
		return regime;
	}

	public void setRegime(String regime) {
		this.regime = regime;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

}
