package br.gov.ma.tce.reunire.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@MappedSuperclass
public abstract class EntidadeBase implements Serializable {
	
	private static final long serialVersionUID = -6521284170299683044L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CRIACAO")
	private Date dataCriacao;
	
	@Version
	@Column(name="VERSAO", columnDefinition="TINYINT(3)")
	private int versao;
	
	public Date getDataCriacao() {
		return dataCriacao;
	}
	
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public int getVersao() {
		return versao;
	}
	
	public void setVersao(int versao) {
		this.versao = versao;
	}
}