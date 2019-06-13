package com.rancotech.tendtudo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rancotech.tendtudo.model.enumerated.StatusAtivo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "tipos")
public class Tipo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "tipo")
	private String nome;

	@Column(name = "ativo")
	@Enumerated
	@JsonIgnore
	private StatusAtivo ativo;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@PrePersist
	private void prePersist() {
		this.createdAt =  LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public StatusAtivo getAtivo() {
		return ativo;
	}

	public void setAtivo(StatusAtivo ativo) {
		this.ativo = ativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tipo other = (Tipo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
