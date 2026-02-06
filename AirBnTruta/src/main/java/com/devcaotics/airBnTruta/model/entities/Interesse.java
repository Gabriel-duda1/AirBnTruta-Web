
package com.devcaotics.airBnTruta.model.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ALUNO
 */
public class Interesse {
    
    private int codigo;
    private long realizado;
    private String proposta;
    private int tempoPermanencia;
    
    public Interesse(){
        this.realizado = new Date().getTime();
    }
    
    private Fugitivo interessado;
    private Hospedagem interesse;
    
    

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public long getRealizado() {
        return realizado;
    }

    public void setRealizado(long realizado) {
        this.realizado = realizado;
    }

    public String getProposta() {
        return proposta;
    }

    public void setProposta(String proposta) {
        this.proposta = proposta;
    }

    public int getTempoPermanencia() {
        return tempoPermanencia;
    }

    public void setTempoPermanencia(int tempoPermanencia) {
        this.tempoPermanencia = tempoPermanencia;
    }

    public Fugitivo getInteressado() {
        return interessado;
    }

    public void setInteressado(Fugitivo interessado) {
        this.interessado = interessado;
    }

    public Hospedagem getInteresse() {
        return interesse;
    }

    public void setInteresse(Hospedagem interesse) {
        this.interesse = interesse;
    }
    
    public void setRealizado(String data){
        
        try {
            this.realizado = new SimpleDateFormat("dd/MM/yyyy").parse(data).getTime();
        } catch (ParseException ex) {
            System.getLogger(Interesse.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }
    
    public String getRealizadoFormatado(){
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date(this.realizado));
    }
    
    
    
}
