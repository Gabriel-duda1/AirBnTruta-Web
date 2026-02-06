package com.devcaotics.airBnTruta.model.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.devcaotics.airBnTruta.model.entities.Fugitivo;
import com.devcaotics.airBnTruta.model.entities.Hospedagem;
import com.devcaotics.airBnTruta.model.entities.Hospedeiro;
import com.devcaotics.airBnTruta.model.entities.Interesse;
import com.devcaotics.airBnTruta.model.entities.Servico;

@org.springframework.stereotype.Repository
public class Facade {

    private static Facade myself = null;

    private Repository<Servico, Integer> rServico;
    private Repository<Fugitivo, Integer> rFugitivo;
    private Repository<Hospedeiro, Integer> rHospedeiro;
    private Repository<Hospedagem, Integer> rHospedagem;
    private Repository<Interesse, Integer> rInteresse;

    public Facade() {
        rServico = new ServicoRepository();
        this.rFugitivo = new FugitivoRepository();
        this.rHospedeiro = new HospedeiroRepository();
        this.rHospedagem = new HospedagemRepository();
        this.rInteresse = new InteresseRepository();
    }

    public static Facade getCurrentInstance() {
        if (myself == null) {
            myself = new Facade();
        }
        return myself;
    }

    // --- MÉTODOS DE SERVIÇO ---
    public void create(Servico s) throws SQLException {
        this.rServico.create(s);
    }

    public void update(Servico s) throws SQLException {
        this.rServico.update(s);
    }

    public Servico readServico(int codigo) throws SQLException {
        return this.rServico.read(codigo);
    }

    public void deleteServico(int codigo) throws SQLException {
        this.rServico.delete(codigo);
    }

    public List<Servico> readAllServico() throws SQLException {
        return this.rServico.readAll();
    }

    // --- MÉTODOS DE FUGITIVO ---
    public void create(Fugitivo f) throws SQLException {
        this.rFugitivo.create(f);
    }

    public void update(Fugitivo f) throws SQLException {
        this.rFugitivo.update(f);
    }

    public Fugitivo readFugitivo(int codigo) throws SQLException {
        return this.rFugitivo.read(codigo);
    }

    public Fugitivo loginFugitivo(String vulgo, String senha) throws SQLException {
        return ((FugitivoRepository) this.rFugitivo).login(vulgo, senha);
    }

    // --- MÉTODOS DE HOSPEDEIRO ---
    public void create(Hospedeiro h) throws SQLException {
        this.rHospedeiro.create(h);
    }

    public Hospedeiro loginHospedeiro(String vulgo, String senha) throws SQLException {
        return ((HospedeiroRepository) this.rHospedeiro).login(vulgo, senha);
    }

    // --- MÉTODOS DE HOSPEDAGEM ---
    public void create(Hospedagem h) throws SQLException {
        this.rHospedagem.create(h);
    }

    public Hospedagem readHospedagem(int codigo) throws SQLException {
        return this.rHospedagem.read(codigo);
    }

    public List<Hospedagem> filterHospedagemByAvailable() throws SQLException {
        return ((HospedagemRepository) this.rHospedagem).filterByAvailable();
    }

    public List<Hospedagem> filterHospedagemByHospedeiro(int codigoHospedeiro) throws SQLException {
        return ((HospedagemRepository) this.rHospedagem).filterByHospedeiro(codigoHospedeiro);
    }

    public List<Hospedagem> filterHospedagempBySelection(String localidade, Double precoMax) throws SQLException {
        return ((HospedagemRepository) this.rHospedagem).filterByFugitivoSelection(localidade, precoMax);
    }

    public boolean hospedagemDisponivel(int hospedagemId) throws SQLException {
        return ((HospedagemRepository) rHospedagem).estaDisponivel(hospedagemId);
    }

    // --- MÉTODOS DE INTERESSE ---
    public void create(Interesse i) throws SQLException {
        this.rInteresse.create(i);
    }

    public List<Interesse> readInteressesByFugitivo(int fugitivoId) throws SQLException {
        return ((InteresseRepository) this.rInteresse).filterByFugitivo(fugitivoId);
    }

    public boolean existeInteresse(int fugitivoId, int hospedagemId) throws SQLException {
        return ((InteresseRepository) rInteresse).existeInteressePendente(fugitivoId, hospedagemId);
    }

    public int quantidadeInteressesHospedagem(int hospId) throws SQLException {
        return ((InteresseRepository) this.rInteresse).countInteressesPorHospedagem(hospId);
    }

    // Este método chama o Repository 
    public List<Interesse> readInteressesPorHospedagem(int hospId) throws SQLException {
        return ((InteresseRepository) this.rInteresse).filterByHospedagem(hospId);
    }

    // --- LÓGICA DE ACEITE  ---
    public void aceitarFugitivoNaHospedagem(int hospId, int fugitivoId) throws SQLException {
        ((HospedagemRepository) this.rHospedagem).aceitarFugitivo(hospId, fugitivoId);

        // 2. Marca o interesse como concluído para que ele suma das listas de pendentes
        String sql = "UPDATE interesse SET realizado = 1 WHERE hospedagem_id = ? AND fugitivo_id = ?";

        try (PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql)) {
            stmt.setInt(1, hospId);
            stmt.setInt(2, fugitivoId);
            stmt.executeUpdate();
        }
    }
}
