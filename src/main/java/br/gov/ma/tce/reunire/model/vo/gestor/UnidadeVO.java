package br.gov.ma.tce.reunire.model.vo.gestor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(schema="GESTOR", name="UNIDADE")
public class UnidadeVO {
	
	@Id
	@Column(name="UNIDADE_ID")
	private Integer id;
	
	@Column(name="NOME")
	private String nome;
	
	@ManyToOne
	@JoinColumn(name="ORGAO_ID")
	private OrgaoVO orgao;
	
	@Column(name="IDENTIFICACAO_LANCAMENTO_UG_ID")
	private Integer identificacaoLancamentoUg;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public OrgaoVO getOrgao() {
		return orgao;
	}
	
	public void setOrgao(OrgaoVO orgao) {
		this.orgao = orgao;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getIdentificacaoLancamentoUg() {
		return identificacaoLancamentoUg;
	}
	
	public void setIdentificacaoLancamentoUg(Integer identificacaoLancamentoUg) {
		this.identificacaoLancamentoUg = identificacaoLancamentoUg;
	}
}