package com.devcaotics.airBnTruta.model.repositories;

import com.devcaotics.airBnTruta.model.entities.Fugitivo;
import com.devcaotics.airBnTruta.model.entities.Hospedagem;
import com.devcaotics.airBnTruta.model.entities.Hospedeiro;
import com.devcaotics.airBnTruta.model.entities.Servico;
import java.sql.SQLException;
import java.util.List;


@org.springframework.stereotype.Repository
public class Facade {

    private static Facade myself = null;

    private Repository<Servico,Integer> rServico;
    private Repository<Fugitivo, Integer> rFugitivo;
    private Repository<Hospedeiro, Integer> rHospedeiro;
    private Repository<Hospedagem, Integer> rHospedagem;

    public Facade(){
        rServico = new ServicoRepository();
        this.rFugitivo = new FugitivoRepository();
        this.rHospedeiro = new HospedeiroRepository();
        this.rHospedagem = new HospedagemRepository();
    }

    public static Facade getCurrentInstance(){

        if(myself == null)
            myself = new Facade();

        return myself;
    }

    public void create(Servico s) throws SQLException{
        this.rServico.create(s);
    }

    public void update(Servico s) throws SQLException{
        this.rServico.update(s);
    }

    public Servico readServico(int codigo) throws SQLException{
        return this.rServico.read(codigo);
    }

    public void deleteServico(int codigo) throws SQLException{
        this.rServico.delete(codigo);
    }

    public List<Servico> readAllServico() throws SQLException{
        return this.rServico.readAll();
    }

    public void create(Fugitivo f) throws SQLException{
        this.rFugitivo.create(f);
    }

    public void update(Fugitivo f) throws SQLException{
        this.rFugitivo.update(f);
    }

    public Fugitivo readFugitivo(int codigo) throws SQLException{
        return this.rFugitivo.read(codigo);
    }

    public Fugitivo loginFugitivo(String vulgo, String senha) throws SQLException{
        return ((FugitivoRepository)this.rFugitivo).login(vulgo,senha);
    }

    public void create(Hospedeiro h) throws SQLException{
        this.rHospedeiro.create(h);
    }

    public Hospedeiro loginHospedeiro(String vulgo, String senha) throws SQLException{
        return ((HospedeiroRepository)this.rHospedeiro).login(vulgo, senha);

    }

    public void create(Hospedagem h) throws SQLException{
        this.rHospedagem.create(h);
    }

    public Hospedagem readHospedagem(int codigo) throws SQLException{
        return this.rHospedagem.read(codigo);
    }

    public List<Hospedagem> filterHospedagemByAvailable() throws SQLException{
        return ((HospedagemRepository)this.rHospedagem).filterByAvailable();
    }

    public List<Hospedagem> filterHospedagemByHospedeiro(int codigoHospedeiro) throws SQLException{
        return ((HospedagemRepository)this.rHospedagem).filterByHospedeiro(codigoHospedeiro);
    }

}
