package br.gov.ma.tce.reunire.model.vo.gestor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema="GESTOR", name="PODER")
public class PoderVO {
	
	@Id
	@Column(name="PODER_ID")
	private int id;
	
	@Column(name="NOME")
	private String nome;
}
