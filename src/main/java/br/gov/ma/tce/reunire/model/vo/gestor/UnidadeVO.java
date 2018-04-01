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
	private int id;
	
	@Column(name="NOME")
	private String nome;
	
	@ManyToOne
	@JoinColumn(name="ORGAO_ID")
	private OrgaoVO orgao;
	
	public int getId() {
		return id;
	}
}