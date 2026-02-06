package com.devcaotics.airBnTruta.model.entities;

public class Fugitivo{

    private int codigo;
    private String nome;
    private String vulgo;
    private String especialidade;
    private String faccao;
    private String descricao;
    private String senha;

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getVulgo() {
        return vulgo;
    }
    public void setVulgo(String vulgo) {
        this.vulgo = vulgo;
    }
    public String getEspecialidade() {
        return especialidade;
    }
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
    public String getFaccao() {
        return faccao;
    }
    public void setFaccao(String faccao) {
        this.faccao = faccao;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

}