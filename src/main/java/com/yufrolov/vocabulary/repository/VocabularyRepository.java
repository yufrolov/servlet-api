package com.yufrolov.vocabulary.repository;

import com.yufrolov.vocabulary.entity.Vocabulary;
import com.yufrolov.vocabulary.exception.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VocabularyRepository {
    public static final String INSERT_QUERY_TEMPLATE =
            "insert into vocabulary(profile_id, word, translate_word) " +
                    "values(?,?,?);";
    public static final String SELECT_PROFILE_ID_QUERY_TEMPLATE = "select * from vocabulary where profile_id=?;";
    public static final String SELECT_ID_QUERY_TEMPLATE = "select * from vocabulary where id=?;";
    public static final String DELETE_QUERY_TEMPLATE = "delete from vocabulary where id=?;";
    public static final String UPDATE_QUERY_TEMPLATE = "update vocabulary set word=?, translate_word=?  where id=?;";

    private Vocabulary mapVocabulary(ResultSet rs) throws SQLException {
        return new Vocabulary(
                rs.getLong("id")
                , rs.getLong("profile_id")
                , rs.getString("word")
                , rs.getString("translate_word"));
    }

    public List<Vocabulary> findByProfileId(Long profileId) {

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_PROFILE_ID_QUERY_TEMPLATE)) {
            ps.setLong(1, profileId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                List<Vocabulary> vocabularies = new ArrayList<>();
                do {
                    vocabularies.add(mapVocabulary(rs));
                } while (rs.next());
                return vocabularies;
            }
            return new ArrayList<>();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public Vocabulary findById(Long id) {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ID_QUERY_TEMPLATE)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapVocabulary(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public boolean create(Long profileId, String word, String translateWord) {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_QUERY_TEMPLATE)) {
            ps.setLong(1, profileId);
            ps.setString(2, word);
            ps.setString(3, translateWord);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public boolean deleteById(Long id) {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_QUERY_TEMPLATE)) {
            ps.setLong(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public boolean updateById(String word, String translateWord, Long id) {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_QUERY_TEMPLATE)) {
            ps.setString(1, word);
            ps.setString(2, translateWord);
            ps.setLong(3, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
