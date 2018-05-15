package br.gov.ma.tce.reunire.model.vo;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="SAE_PPA")
public class PpaTextoDaLeiVO implements Serializable {
	
	private static final long serialVersionUID = -9023663347383906236L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_PPA")
	private int id;
	
	@Column(name="TEXTO_LEI")
	@Lob
	private byte[] textoDaLei;
	
	@Column(name="ENTE")
	private int ente;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getTextoDaLei() throws UnsupportedEncodingException {
		return textoDaLei;
	}

	public void setTextoDaLei(byte[] textoDaLei) {
		this.textoDaLei = textoDaLei;
	}

	public int getEnte() {
		return ente;
	}

	public void setEnte(int ente) {
		this.ente = ente;
	}

}
