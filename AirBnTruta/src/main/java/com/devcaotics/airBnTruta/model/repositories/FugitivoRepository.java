package com.devcaotics.airBnTruta.model.repositories;

import com.devcaotics.airBnTruta.model.entities.Fugitivo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public final class FugitivoRepository implements Repository<Fugitivo,Integer>{

    protected FugitivoRepository(){}

    @Override
    public void create(Fugitivo c) throws SQLException {
       String sql = "INSERT INTO fugitivo (nome, vulgo, especialidade, faccao, descricao, senha) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

       Connection conn = ConnectionManager.getCurrentConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getVulgo());
            stmt.setString(3, c.getEspecialidade());
            stmt.setString(4, c.getFaccao());
            stmt.setString(5, c.getDescricao());
            stmt.setString(6, c.getSenha());

            stmt.execute();

        
    }

    @Override
    public void update(Fugitivo c) throws SQLException {
        String sql = "UPDATE fugitivo SET nome=?, vulgo=?, especialidade=?, faccao=?, "
                   + "descricao=? WHERE codigo=?";

        Connection conn = ConnectionManager.getCurrentConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getVulgo());
            stmt.setString(3, c.getEspecialidade());
            stmt.setString(4, c.getFaccao());
            stmt.setString(5, c.getDescricao());
            

            stmt.setInt(6,c.getCodigo());

            stmt.executeUpdate();

       
    }

    @Override
    public Fugitivo read(Integer k) throws SQLException {
        String sql = "SELECT * FROM fugitivo WHERE codigo=?";
        Fugitivo f = null;

        Connection conn = ConnectionManager.getCurrentConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, k);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                f = new Fugitivo();
                f.setCodigo(rs.getInt("codigo"));
                f.setNome(rs.getString("nome"));
                f.setVulgo(rs.getString("vulgo"));
                f.setEspecialidade(rs.getString("especialidade"));
                f.setFaccao(rs.getString("faccao"));
                f.setDescricao(rs.getString("descricao"));
                f.setSenha(rs.getString("senha"));
            }

       
        return f;
    }

    @Override
    public void delete(Integer k) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<Fugitivo> readAll() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readAll'");
    }

    public Fugitivo login(String vulgo, String senha) throws SQLException{
        String sql = "SELECT * FROM fugitivo WHERE vulgo=? and senha=?";
        Fugitivo f = null;

        Connection conn = ConnectionManager.getCurrentConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, vulgo);
            stmt.setString(2,senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                f = new Fugitivo();
                f.setCodigo(rs.getInt("codigo"));
                f.setNome(rs.getString("nome"));
                f.setVulgo(rs.getString("vulgo"));
                f.setEspecialidade(rs.getString("especialidade"));
                f.setFaccao(rs.getString("faccao"));
                f.setDescricao(rs.getString("descricao"));
                f.setSenha(rs.getString("senha"));
            }

       
        return f;
    }

}
