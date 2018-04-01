package br.gov.ma.tce.reunire.model.vo.gestor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(schema="GESTOR", name="TIPO_ORGAO")
public class TipoOrgaoVO {
	
	@Id
	@Column(name="TIPO_ORGAO_ID")
	private int id;
	
	@Column(name="NOME")
	private String nome;
	
	@ManyToOne
	@JoinColumn(name="PODER_ID")
	private PoderVO poder;
}
