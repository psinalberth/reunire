package br.gov.ma.tce.reunire.model.vo.d;

import java.math.BigDecimal;

public class RelatorioD002BVO {
	
	private Integer idUnidade;
	private String descricaoUnidade;
	private String codigoCategoriaEconomica;
	private String descricaoCategoriaEconomica;
	private String codigoGrupoDespesa;
	private String descricaoGrupoDespesa;
	private String codigoModalidadeAplicacao;
	private String descricaoModalidadeAplicacao;
	private String codigoElementoDespesa;
	private String descricaoElementoDespesa;
	private BigDecimal valor;
	
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

	public String getCodigoCategoriaEconomica() {
		return codigoCategoriaEconomica;
	}

	public void setCodigoCategoriaEconomica(String codigoCategoriaEconomica) {
		this.codigoCategoriaEconomica = codigoCategoriaEconomica;
	}

	public String getDescricaoCategoriaEconomica() {
		return descricaoCategoriaEconomica;
	}

	public void setDescricaoCategoriaEconomica(String descricaoCategoriaEconomica) {
		this.descricaoCategoriaEconomica = descricaoCategoriaEconomica;
	}

	public String getCodigoGrupoDespesa() {
		return codigoGrupoDespesa;
	}

	public void setCodigoGrupoDespesa(String codigoGrupoDespesa) {
		this.codigoGrupoDespesa = codigoGrupoDespesa;
	}

	public String getDescricaoGrupoDespesa() {
		return descricaoGrupoDespesa;
	}

	public void setDescricaoGrupoDespesa(String descricaoGrupoDespesa) {
		this.descricaoGrupoDespesa = descricaoGrupoDespesa;
	}

	public String getCodigoModalidadeAplicacao() {
		return codigoModalidadeAplicacao;
	}

	public void setCodigoModalidadeAplicacao(String codigoModalidadeAplicacao) {
		this.codigoModalidadeAplicacao = codigoModalidadeAplicacao;
	}

	public String getDescricaoModalidadeAplicacao() {
		return descricaoModalidadeAplicacao;
	}

	public void setDescricaoModalidadeAplicacao(String descricaoModalidadeAplicacao) {
		this.descricaoModalidadeAplicacao = descricaoModalidadeAplicacao;
	}

	public String getCodigoElementoDespesa() {
		return codigoElementoDespesa;
	}

	public void setCodigoElementoDespesa(String codigoElementoDespesa) {
		this.codigoElementoDespesa = codigoElementoDespesa;
	}

	public String getDescricaoElementoDespesa() {
		return descricaoElementoDespesa;
	}

	public void setDescricaoElementoDespesa(String descricaoElementoDespesa) {
		this.descricaoElementoDespesa = descricaoElementoDespesa;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
