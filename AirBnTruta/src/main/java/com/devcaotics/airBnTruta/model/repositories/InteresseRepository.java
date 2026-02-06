package com.devcaotics.airBnTruta.model.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.devcaotics.airBnTruta.model.entities.Fugitivo;
import com.devcaotics.airBnTruta.model.entities.Interesse;

public final class InteresseRepository implements Repository<Interesse, Integer> {

    protected InteresseRepository() {
    }

    @Override
    public void create(Interesse i) throws SQLException {
        String sql = "INSERT INTO interesse (realizado, proposta, tempo_permanencia, fugitivo_id, hospedagem_id) "
                + "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        stmt.setLong(1, i.getRealizado());
        stmt.setString(2, i.getProposta());
        stmt.setInt(3, i.getTempoPermanencia());
        stmt.setInt(4, i.getInteressado().getCodigo());
        stmt.setInt(5, i.getInteresse().getCodigo());

        stmt.execute();
        stmt.close();
    }

    public List<Interesse> filterByFugitivo(int fugitivoId) throws SQLException {
        List<Interesse> interesses = new ArrayList<>();
        String sql = "SELECT * FROM interesse WHERE fugitivo_id = ? AND realizado = 0";

        PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        stmt.setInt(1, fugitivoId);
        ResultSet rs = stmt.executeQuery();

        HospedagemRepository hospRepo = new HospedagemRepository();

        while (rs.next()) {
            Interesse i = new Interesse();
            i.setCodigo(rs.getInt("codigo"));
            i.setProposta(rs.getString("proposta"));
            i.setTempoPermanencia(rs.getInt("tempo_permanencia"));
            i.setRealizado(rs.getLong("realizado"));

            Fugitivo f = new Fugitivo();
            f.setCodigo(rs.getInt("fugitivo_id"));
            i.setInteressado(f);

            // Carregando hospedagem completa para evitar campos vazios na tela
            i.setInteresse(hospRepo.read(rs.getInt("hospedagem_id")));

            interesses.add(i);
        }
        rs.close();
        stmt.close();
        return interesses;
    }

    public List<Interesse> filterByHospedagem(int hospId) throws SQLException {
        List<Interesse> lista = new ArrayList<>();
        String sql = "SELECT * FROM interesse WHERE hospedagem_id = ? AND realizado = 0";

        PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        stmt.setInt(1, hospId);
        ResultSet rs = stmt.executeQuery();

        FugitivoRepository fugRepo = new FugitivoRepository();

        while (rs.next()) {
            Interesse i = new Interesse();
            i.setCodigo(rs.getInt("codigo"));
            i.setProposta(rs.getString("proposta"));
            i.setTempoPermanencia(rs.getInt("tempo_permanencia"));
            i.setRealizado(rs.getLong("realizado"));

            // Carregando o fugitivo completo para o hospedeiro ver o Vulgo
            i.setInteressado(fugRepo.read(rs.getInt("fugitivo_id")));

            lista.add(i);
        }
        rs.close();
        stmt.close();
        return lista;
    }

    public List<Interesse> listarPorFugitivo(int fugitivoId) throws SQLException {
        String sql = """
        SELECT i.* FROM interesse i
        JOIN hospedagem h ON h.codigo = i.hospedagem_id
        WHERE i.fugitivo_id = ? 
        AND i.realizado = 0 
        AND h.fugitivo_id IS NULL
    """;
        // O 'h.fugitivo_id IS NULL' garante que se alguém (você ou outro) 
        // for aceito, ela some da lista de "ainda disponíveis".

        PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        stmt.setInt(1, fugitivoId);
        ResultSet rs = stmt.executeQuery();

        List<Interesse> lista = new ArrayList<>();
        HospedagemRepository hospRepo = new HospedagemRepository(); // NECESSÁRIO

        while (rs.next()) {
            Interesse i = new Interesse();
            i.setCodigo(rs.getInt("codigo"));
            i.setProposta(rs.getString("proposta"));
            i.setTempoPermanencia(rs.getInt("tempo_permanencia"));

            // ADICIONE ESTA LINHA ABAIXO PARA CARREGAR LOCALIDADE E PREÇO
            i.setInteresse(hospRepo.read(rs.getInt("hospedagem_id")));

            lista.add(i);
        }
        rs.close();
        stmt.close();
        return lista;
    }

    @Override
    public void update(Interesse c) throws SQLException {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Interesse read(Integer k) throws SQLException {
        throw new UnsupportedOperationException("Unimplemented method 'read'");
    }

    @Override
    public void delete(Integer k) throws SQLException {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<Interesse> readAll() throws SQLException {
        throw new UnsupportedOperationException("Unimplemented method 'readAll'");
    }

    public boolean existeInteressePendente(int fugitivoId, int hospedagemId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM interesse WHERE fugitivo_id = ? AND hospedagem_id = ? AND realizado = 0";
        PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        stmt.setInt(1, fugitivoId);
        stmt.setInt(2, hospedagemId);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;
    }

    public int countInteressesPorHospedagem(int hospId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM interesse WHERE hospedagem_id = ? AND realizado = 0";
        PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        stmt.setInt(1, hospId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }
}
