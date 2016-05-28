package org.xine.cascateddemos.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ModeloCarro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	private String descricao;

	protected Long getCodigo() {
		return codigo;
	}

	protected void setCodigo(final Long codigo) {
		this.codigo = codigo;
	}

	protected String getDescricao() {
		return descricao;
	}

	protected void setDescricao(final String descricao) {
		this.descricao = descricao;
	}


}
