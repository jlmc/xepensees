package org.xine.cascateddemos.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Carro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	/*
	 * when criar um carro salvar tambem o modelo se ele não estiver criado ,
	 * porque temos cascaced.PERSIST
	 * 
	 * Sem cascade = { CascadeType.PERSIST, CascadeType.MERGE } iria mos obter
	 * 
	 * 
	 * 
	 */
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "codigo_modelo")
	private ModeloCarro modelo;
}
