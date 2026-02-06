package com.devcaotics.airBnTruta.model.repositories;

import com.devcaotics.airBnTruta.model.entities.Hospedeiro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public final class HospedeiroRepository implements Repository<Hospedeiro, Integer> {

    protected HospedeiroRepository() {
    }

    @Override
    public void create(Hospedeiro h) throws SQLException {
        String sql = "INSERT INTO hospedeiro (nome, vulgo, contato, senha) VALUES (?, ?, ?, ?)";

        Connection conn = ConnectionManager.getCurrentConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, h.getNome());
        stmt.setString(2, h.getVulgo());
        stmt.setString(3, h.getContato());
        stmt.setString(4, h.getSenha());

        stmt.execute();

    }

    @Override
    public void update(Hospedeiro c) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Hospedeiro read(Integer k) throws SQLException {
        String sql = "SELECT * FROM hospedeiro WHERE codigo=?";
        Hospedeiro h = null;

        Connection conn = ConnectionManager.getCurrentConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, k);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            h = new Hospedeiro();
            h.setCodigo(rs.getInt("codigo"));
            h.setNome(rs.getString("nome"));
            h.setVulgo(rs.getString("vulgo"));
            h.setContato(rs.getString("contato"));
        }

        return h;
    }

    @Override
    public void delete(Integer k) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<Hospedeiro> readAll() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readAll'");
    }

    public Hospedeiro login(String vulgo, String senha) throws SQLException {
        String sql = "SELECT * FROM hospedeiro WHERE vulgo=? and senha=?";
        Hospedeiro h = null;

        Connection conn = ConnectionManager.getCurrentConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, vulgo);
        stmt.setString(2, senha);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            h = new Hospedeiro();
            h.setCodigo(rs.getInt("codigo"));
            h.setNome(rs.getString("nome"));
            h.setVulgo(rs.getString("vulgo"));
            h.setContato(rs.getString("contato"));
        }

        return h;
    }

}
