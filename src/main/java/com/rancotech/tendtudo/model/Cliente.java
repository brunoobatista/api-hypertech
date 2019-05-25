package com.rancotech.tendtudo.model;

import com.rancotech.tendtudo.model.enumerated.TipoPessoa;
import com.rancotech.tendtudo.model.validation.ClienteGroupSequenceProvider;
import com.rancotech.tendtudo.validation.AtributosConfirmacao;
import com.rancotech.tendtudo.validation.CpfCnpjUnique;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@AtributosConfirmacao(atributo = "password", atributoConfirmacao = "confirmPassword", id = "id", message = "Confirmação da senha não confere")
@CpfCnpjUnique(cpfCnpj = "cpfCnpj", id = "id", message = "CPF/CNPJ já existentes")
@Entity
@Table(name = "cliente")
@GroupSequenceProvider(ClienteGroupSequenceProvider.class)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nome;

    @Email
    @NotEmpty
    private String email;

    private String password;

    @Transient
    private String confirmPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa")
    private TipoPessoa tipoPessoa;

    @NotEmpty
    @Column(name = "cpf_cnpj")
    private String cpfCnpj;

    @PrePersist @PreUpdate
    private void prePersistPreUpdate() {
        this.cpfCnpj = TipoPessoa.removerFormatacao(this.cpfCnpj);
    }

    @PostLoad
    @PostUpdate
    private void postLoad() {
        this.cpfCnpj = this.tipoPessoa.formatar(this.cpfCnpj);
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
