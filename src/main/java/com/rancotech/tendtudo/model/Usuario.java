package com.rancotech.tendtudo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rancotech.tendtudo.model.enumerated.StatusAtivo;
import com.rancotech.tendtudo.model.enumerated.TipoPessoa;
import com.rancotech.tendtudo.validation.CpfCnpjUnique;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "usuarios")
@CpfCnpjUnique(cpfCnpj = "cpf", id = "id", message = "CPF já existentes")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nome;

    @Email
    @NotEmpty
    private String email;

    @Column(name = "password")
    private String password;

    private String username;

    @Column(name = "ativo")
    @Enumerated
    private StatusAtivo ativo;

    @Transient
    private String confirmPassword;

    private String cpf;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "usuarios_roles",
        joinColumns = @JoinColumn(
            name = "usuario_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(
            name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.setCpfCnpjDoc();
    }

    @PreUpdate
    private void preUpdate() {
        this.setCpfCnpjDoc();
    }

    private void setCpfCnpjDoc() {
        if (this.cpf == null || this.cpf.isEmpty()) {
            this.cpf = null;
        } else {
            this.cpf = TipoPessoa.removerFormatacao(this.cpf);
        }
    }

    @PostLoad
    @PostUpdate
    private void postLoad() {
        if (this.cpf != null && !this.cpf.isEmpty())
            this.cpf = TipoPessoa.formatarCpf(this.cpf);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public StatusAtivo getAtivo() {
        return ativo;
    }

    public void setAtivo(StatusAtivo ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
