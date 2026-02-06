package com.devcaotics.airBnTruta.model.repositories;

import com.devcaotics.airBnTruta.model.entities.Interesse;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public final class InteresseRepository implements Repository<Interesse,Integer>{

    protected InteresseRepository(){}

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
    }

    @Override
    public void update(Interesse c) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Interesse read(Integer k) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'read'");
    }

    @Override
    public void delete(Integer k) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<Interesse> readAll() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readAll'");
    }

}
