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
}