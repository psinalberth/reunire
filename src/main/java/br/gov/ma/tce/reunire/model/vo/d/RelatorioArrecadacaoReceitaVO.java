package br.gov.ma.tce.reunire.model.vo.d;

import java.math.BigDecimal;
import java.util.Date;

public class RelatorioArrecadacaoReceitaVO {
	
	protected static final long serialVersionUID = 4376323486224609426L;
	
	private Integer idUnidade;
	private String descricaoUnidade;
	private Date dataContabil;
	private String numeroDocumento;
	private String historico;
	private String credorNaturezaReceita;
	private String codigoNatureza;
	private String descricaoNaturezaReceita;
	private BigDecimal valorArrecadacao;
	
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

	public Date getDataContabil() {
		return dataContabil;
	}

	public void setDataContabil(Date dataContabil) {
		this.dataContabil = dataContabil;
	}
	
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public String getCredorNaturezaReceita() {
		return credorNaturezaReceita;
	}

	public void setCredorNaturezaReceita(String credorNaturezaReceita) {
		this.credorNaturezaReceita = credorNaturezaReceita;
	}

	public String getCodigoNatureza() {
		return codigoNatureza;
	}

	public void setCodigoNatureza(String codigoNatureza) {
		this.codigoNatureza = codigoNatureza;
	}

	public String getDescricaoNaturezaReceita() {
		return descricaoNaturezaReceita;
	}

	public void setDescricaoNaturezaReceita(String descricaoNaturezaReceita) {
		this.descricaoNaturezaReceita = descricaoNaturezaReceita;
	}

	public BigDecimal getValorArrecadacao() {
		return valorArrecadacao;
	}

	public void setValorArrecadacao(BigDecimal valorArrecadacao) {
		this.valorArrecadacao = valorArrecadacao;
	}

	

}
