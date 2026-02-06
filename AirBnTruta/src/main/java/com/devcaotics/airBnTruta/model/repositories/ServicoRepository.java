package com.devcaotics.airBnTruta.model.repositories;

import com.devcaotics.airBnTruta.model.entities.Servico;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ServicoRepository implements Repository<Servico, Integer>{

    protected ServicoRepository(){}

    @Override
    public void create(Servico c) throws SQLException {

        String sql = "Insert into Servico(nome,tipo,descricao) "+
        "values(?,?,?)"; 
        
        PreparedStatement pstm = ConnectionManager.getCurrentConnection()
            .prepareStatement(sql);

        pstm.setString(1, c.getNome());
        pstm.setString(2, c.getTipo());
        pstm.setBytes(3, c.getDescricao().getBytes());

        pstm.execute();
    
    }

    @Override
    public void update(Servico c) throws SQLException {
        String sql = "update servico set"+
            " nome = ?,tipo = ?,descricao = ? where codigo = ?";

        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);

        pstm.setString(1, c.getNome());
        pstm.setString(2, c.getTipo());
        pstm.setBytes(3, c.getDescricao().getBytes());
        pstm.setInt(4, c.getCodigo());

        pstm.execute();

    }

    @Override
    public Servico read(Integer k) throws SQLException {
        
        String sql = "select * from servico where codigo=?";

        PreparedStatement pstm = ConnectionManager.getCurrentConnection().prepareStatement(sql);

        pstm.setInt(1, k);

        ResultSet result = pstm.executeQuery();

        if(result.next()){

            Servico s = new Servico();

            s.setCodigo(result.getInt("codigo"));
            s.setNome(result.getString("nome"));
            s.setTipo(result.getString("tipo"));
            s.setDescricao(new String(result.getBytes("descricao")));

            return s;

        }

        return null;
    
    }

    @Override
    public void delete(Integer k) throws SQLException {
    
        String sql = "delete from servico where codigo = "+k;

        ConnectionManager.getCurrentConnection().prepareStatement(sql).execute();
    
    }

    @Override
    public List<Servico> readAll() throws SQLException {

        String sql = "select * from servico";

        ResultSet result = ConnectionManager.getCurrentConnection().prepareStatement(sql)
            .executeQuery();

        List<Servico> servicos = new ArrayList<>();

        while(result.next()){
            Servico s = new Servico();

            s.setCodigo(result.getInt("codigo"));
            s.setNome(result.getString("nome"));
            s.setTipo(result.getString("tipo"));
            s.setDescricao(new String(result.getBytes("descricao")));

            servicos.add(s);
        }

        return servicos;

    }

    public List<Servico> filterByHospedagem(int hospedagemId) throws SQLException{
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

    public static void main(String args[]){
        Servico s = new Servico();
        s.setNome("Copera");
        s.setTipo("pessoal");
        s.setDescricao("Serviços gerais de limpesa e cafezinho e bolachinha");
        s.setCodigo(1);

        try {
            new ServicoRepository().create(s);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /*try {
            //Servico s = new ServicoRepository().read(1);

            new ServicoRepository().delete(1);

            //System.out.println(s.getDescricao());
        } catch (SQLException e) {
            
            e.printStackTrace();
        }*/

        try {
            System.out.println(new ServicoRepository().readAll().size());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
