package br.gov.ma.tce.reunire.model.vo.cam;

import java.math.BigDecimal;
import java.util.Date;

import br.gov.ma.tce.reunire.model.vo.generic.DemonstrativoVO;

public class RelatorioCAM03VO extends DemonstrativoVO {
	
	private Date dataAto;
	private String tipoAto;
	private BigDecimal valor;
	private BigDecimal dotacaoInicial;
	
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
