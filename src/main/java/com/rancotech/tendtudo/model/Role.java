package com.rancotech.tendtudo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Transient
    private String label;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Collection<Usuario> usuarios;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "roles_permissaos",
        joinColumns = @JoinColumn(
                name = "role_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(
                name = "permissao_id", referencedColumnName = "id"))
    private Collection<Permissao> permissaos;

    @PostLoad
    private void setLabel() {
        String txt[] = this.nome.split("_");
        String lb = txt[1].substring(0, 1);
        this.label = lb.concat(txt[1].substring(1).toLowerCase());
    }

    public String getLabel() {
        return this.label;
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

    public Collection<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Collection<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Collection<Permissao> getPermissaos() {
        return permissaos;
    }

    public void setPermissaos(Collection<Permissao> permissaos) {
        this.permissaos = permissaos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
