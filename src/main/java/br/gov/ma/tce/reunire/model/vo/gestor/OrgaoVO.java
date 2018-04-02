package br.gov.ma.tce.reunire.model.vo.gestor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(schema="GESTOR", name="ORGAO")
public class OrgaoVO {
	
	@Id
	@Column(name="ORGAO_ID")
	private int id;
	
	@Column(name="NOME")
	private String nome;
	
	@ManyToOne
	@JoinColumn(name="ENTE_ID")
	private EnteVO ente;
	
	@ManyToOne
	@JoinColumn(name="TIPO_ORGAO_ID")
	private TipoOrgaoVO tipoOrgao;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public EnteVO getEnte() {
		return ente;
	}
	
	public void setEnte(EnteVO ente) {
		this.ente = ente;
	}
	
	public TipoOrgaoVO getTipoOrgao() {
		return tipoOrgao;
	}
	
	public void setTipoOrgao(TipoOrgaoVO tipoOrgao) {
		this.tipoOrgao = tipoOrgao;
	}
}