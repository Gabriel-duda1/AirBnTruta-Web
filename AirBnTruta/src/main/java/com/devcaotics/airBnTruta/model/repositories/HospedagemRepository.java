package com.devcaotics.airBnTruta.model.repositories;

import com.devcaotics.airBnTruta.model.entities.Hospedagem;
import com.devcaotics.airBnTruta.model.entities.Servico;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class HospedagemRepository implements Repository<Hospedagem, Integer> {

    protected HospedagemRepository(){}
    
    @Override
    public void create(Hospedagem h) throws SQLException {
         String sql = "INSERT INTO hospedagem (descricao_curta, descricao_longa, localizacao, diaria, inicio, hospedeiro_id) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, h.getDescricaoCurta());
        stmt.setString(2, h.getDescricaoLonga());
        stmt.setString(3, h.getLocalizacao());
        stmt.setDouble(4, h.getDiaria());
        stmt.setDate(5, new java.sql.Date(h.getInicio().getTime()));
        stmt.setInt(6, h.getHospedeiro().getCodigo());
        

        stmt.execute();

        if (h.getServicos() == null) return;

        ResultSet resultKey = stmt.getGeneratedKeys();

        if(resultKey.next()){
            h.setCodigo(resultKey.getInt(1));
        }

        sql = "INSERT INTO hospedagem_servico (hospedagem_id, servico_id) VALUES (?, ?)";
        PreparedStatement stmtServico = ConnectionManager.getCurrentConnection().prepareStatement(sql);

        for (Servico s : h.getServicos()) {
            stmtServico.setInt(1, h.getCodigo());
            stmtServico.setInt(2, s.getCodigo());
            stmtServico.addBatch();
        }

        stmtServico.executeBatch();
    }

    @Override
    public void update(Hospedagem c) throws SQLException {
        
    }

    @Override
    public Hospedagem read(Integer k) throws SQLException {
         String sql = "SELECT * FROM hospedagem WHERE codigo = ?";
        PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        stmt.setInt(1, k);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) return null;

        Hospedagem h = new Hospedagem();
        h.setCodigo(rs.getInt("codigo"));
        h.setDescricaoCurta(rs.getString("descricao_curta"));
        h.setDescricaoLonga(rs.getString("descricao_longa"));
        h.setLocalizacao(rs.getString("localizacao"));
        h.setDiaria(rs.getDouble("diaria"));
        h.setInicio(rs.getDate("inicio"));
        h.setFim(rs.getDate("fim"));

        // Carregar relacionamentos
        h.setHospedeiro(new HospedeiroRepository().read(rs.getInt("hospedeiro_id")));
        h.setFugitivo(new FugitivoRepository().read(rs.getInt("fugitivo_id")));
        h.setServicos(new ServicoRepository().filterByHospedagem(h.getCodigo()));

        return h;
    }
    

    @Override
    public void delete(Integer k) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    /*@Override
    //Por que este método gerado pelo chatgpt não é eficiente?
    public List<Hospedagem> readAll() throws SQLException {
         String sql = "SELECT codigo FROM hospedagem";
        PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<Hospedagem> lista = new ArrayList<>();

        while (rs.next()) {
            lista.add(read(rs.getInt("codigo")));
        }

        return lista;
    }*/


    public List<Hospedagem> readAll() throws SQLException {

        String sql = "SELECT * FROM hospedagem";

        return filterBy(sql);
       
    }

    /*
        Filter Area
    */


    public List<Hospedagem> filterByAvailable() throws SQLException{
        String sql = "SELECT * FROM hospedagem where codigo_foragido = 0";

        return filterBy(sql);
    }

    public List<Hospedagem> filterByHospedeiro(int codigoHospedeiro) throws SQLException{
        String sql = "select * from hospedagem where hospedeiro_id = "+codigoHospedeiro;

        return filterBy(sql);
    }


    private List<Hospedagem> filterBy(String sql) throws SQLException {

        PreparedStatement stmt = ConnectionManager.getCurrentConnection().prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();

        List<Hospedagem> hospedagens = new ArrayList<>();

        while (rs.next()){

            Hospedagem h = new Hospedagem();
            h.setCodigo(rs.getInt("codigo"));
            h.setDescricaoCurta(rs.getString("descricao_curta"));
            h.setDescricaoLonga(rs.getString("descricao_longa"));
            h.setLocalizacao(rs.getString("localizacao"));
            h.setDiaria(rs.getDouble("diaria"));
            h.setInicio(rs.getDate("inicio"));
            h.setFim(rs.getDate("fim"));

            // Carregar relacionamentos
            h.setHospedeiro(new HospedeiroRepository().read(rs.getInt("hospedeiro_id")));
            h.setFugitivo(new FugitivoRepository().read(rs.getInt("fugitivo_id")));
            h.setServicos(new ServicoRepository().filterByHospedagem(h.getCodigo()));

            hospedagens.add(h);
        }

        return hospedagens;
        

     }


}
