
package com.devcaotics.airBnTruta.model.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ALUNO
 */
public class Hospedagem {
    
    private int codigo;
    private String descricaoCurta;
    private String descricaoLonga;
    private String localizacao;
    private double diaria;
    private Date inicio;
    private Date fim;
    
    private Hospedeiro hospedeiro;
    private Fugitivo fugitivo;
    private List<Servico> servicos;
    
    public Hospedagem(){
        this.inicio = new Date();
        this.servicos = new ArrayList<>();
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricaoCurta() {
        return descricaoCurta;
    }

    public void setDescricaoCurta(String descricaoCurta) {
        this.descricaoCurta = descricaoCurta;
    }

    public String getDescricaoLonga() {
        return descricaoLonga;
    }

    public void setDescricaoLonga(String descricaoLonga) {
        this.descricaoLonga = descricaoLonga;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public double getDiaria() {
        return diaria;
    }

    public void setDiaria(double diaria) {
        this.diaria = diaria;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public Hospedeiro getHospedeiro() {
        return hospedeiro;
    }

    public void setHospedeiro(Hospedeiro hospedeiro) {
        this.hospedeiro = hospedeiro;
    }

    public Fugitivo getFugitivo() {
        return fugitivo;
    }

    public void setFugitivo(Fugitivo fugitivo) {
        this.fugitivo = fugitivo;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }
    
    public void addServico(Servico s){
        this.servicos.add(s);
    }
    
    public String getInicioFormatado(){
        return new SimpleDateFormat("dd/MM/yyyy").format(this.inicio);
    }
    
    public String getFimFormatado(){
        return new SimpleDateFormat("dd/MM/yyyy").format(this.fim);
    }
    
    public void setInicio(String data){
        
        try {
            this.inicio = new SimpleDateFormat("dd/MM/yyyy").parse(data);
        } catch (ParseException ex) {
            System.getLogger(Hospedagem.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }
    
    public void setFim(String data){
        try {
            this.fim = new SimpleDateFormat("dd/MM/yyyy").parse(data);
        } catch (ParseException ex) {
            System.getLogger(Hospedagem.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    
}
