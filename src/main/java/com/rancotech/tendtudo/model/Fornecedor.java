package com.rancotech.tendtudo.model;

import com.rancotech.tendtudo.model.enumerated.TipoPessoa;
import com.rancotech.tendtudo.validation.CpfCnpjUnique;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@CpfCnpjUnique(cpfCnpj = "cpfOuCnpj", id = "id", message = "CPF/CNPJ já existentes")
@Entity
@Table(name =  "fornecedor")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 40)
    private String nome;

    @NotBlank
    @Size(min = 3, max = 60)
    @Column(name = "nome_fantasia")
    private String nomeFantasia;

    @NotBlank
    @Column(name = "cpf_cnpj")
    private String cpfOuCnpj;

    private String telefone;

    private String telefone_op;

    @Embedded
    @JsonIgnore
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;

    @PrePersist @PreUpdate
    private void prePersistPreUpdate() {
        if (this.cpfOuCnpj.isEmpty()) {
            this.cpfOuCnpj = null;
        } else {
            this.cpfOuCnpj = TipoPessoa.removerFormatacao(this.cpfOuCnpj);
        }
    }

    @PostLoad
    @PostUpdate
    private void postLoad() {
        if (this.cpfOuCnpj != null && !this.cpfOuCnpj.isEmpty()) {
            if (this.cpfOuCnpj.length() == 11) {
                this.cpfOuCnpj = TipoPessoa.formatarCpf(this.cpfOuCnpj);
            } else {
                this.cpfOuCnpj = TipoPessoa.formatarCnpj(this.cpfOuCnpj);
            }
        }
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

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefone_op() {
        return telefone_op;
    }

    public void setTelefone_op(String telefone_op) {
        this.telefone_op = telefone_op;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fornecedor that = (Fornecedor) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
