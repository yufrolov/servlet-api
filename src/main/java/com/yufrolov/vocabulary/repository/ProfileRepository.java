package com.yufrolov.vocabulary.repository;

import com.yufrolov.vocabulary.entity.Profile;
import com.yufrolov.vocabulary.exception.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileRepository {

    private static String INSERT_QUERY_TEMPLATE = "INSERT INTO profile (login, password,fio) values (?, ?, ?)";

    public static final String SELECT_QUERY_TEMPLATE = "select * from profile where login=? and password=?;";

    public boolean create(String fio, String login, String password) {

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_QUERY_TEMPLATE)) {
            ps.setString(1, login);
            ps.setString(2, password);
            ps.setString(3, fio);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public Profile findByLoginAndPassword(String login, String password) {

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_QUERY_TEMPLATE)) {
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapProfile(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    private Profile mapProfile(ResultSet rs) throws SQLException {
        return new Profile(
                rs.getLong("id")
                , rs.getString("login")
                , null
                , rs.getString("fio"));
    }
}
