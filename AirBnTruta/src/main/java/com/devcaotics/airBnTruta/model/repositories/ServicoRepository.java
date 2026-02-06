package com.devcaotics.airBnTruta.model.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.devcaotics.airBnTruta.model.entities.Servico;

public final class ServicoRepository implements Repository<Servico, Integer> {

    protected ServicoRepository() {}

    @Override
    public void create(Servico c) throws SQLException {
        String sql = "INSERT INTO servico (nome, tipo, descricao) VALUES (?, ?, ?)";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setString(1, c.getNome());
        pstm.setString(2, c.getTipo());
        pstm.setString(3, c.getDescricao());
        pstm.executeUpdate();
    }

    @Override
    public void update(Servico c) throws SQLException {
        String sql = "UPDATE servico SET nome = ?, tipo = ?, descricao = ? WHERE codigo = ?";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setString(1, c.getNome());
        pstm.setString(2, c.getTipo());
        pstm.setString(3, c.getDescricao());
        pstm.setInt(4, c.getCodigo());
        pstm.executeUpdate();
    }

    @Override
    public Servico read(Integer k) throws SQLException {
        String sql = "SELECT * FROM servico WHERE codigo = ?";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setInt(1, k);
        ResultSet result = pstm.executeQuery();

        if (result.next()) {
            Servico s = new Servico();
            s.setCodigo(result.getInt("codigo"));
            s.setNome(result.getString("nome"));
            s.setTipo(result.getString("tipo"));
            s.setDescricao(result.getString("descricao"));
            return s;
        }
        return null;
    }

    @Override
    public void delete(Integer k) throws SQLException {
        String sql = "DELETE FROM servico WHERE codigo = ?";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        pstm.setInt(1, k);
        pstm.executeUpdate();
    }

    @Override
    public List<Servico> readAll() throws SQLException {
        String sql = "SELECT * FROM servico";
        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        ResultSet result = pstm.executeQuery();

        List<Servico> servicos = new ArrayList<>();
        while (result.next()) {
            Servico s = new Servico();
            s.setCodigo(result.getInt("codigo"));
            s.setNome(result.getString("nome"));
            s.setTipo(result.getString("tipo"));
            s.setDescricao(result.getString("descricao"));
            servicos.add(s);
        }
        return servicos;
    }

    public List<Servico> filterByHospedagem(int hospedagemId) throws SQLException {
        String sql = "SELECT s.* FROM servico s "
                   + "JOIN hospedagem_servico hs ON s.codigo = hs.servico_id "
                   + "WHERE hs.hospedagem_id = ?";
        PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        stmt.setInt(1, hospedagemId);
        ResultSet rs = stmt.executeQuery();

        List<Servico> lista = new ArrayList<>();
        while (rs.next()) {
            Servico s = new Servico();
            s.setCodigo(rs.getInt("codigo"));
            s.setNome(rs.getString("nome"));
            s.setTipo(rs.getString("tipo"));
            s.setDescricao(rs.getString("descricao"));
            lista.add(s);
        }
        return lista;
    }
}