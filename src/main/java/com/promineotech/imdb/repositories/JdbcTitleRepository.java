package com.promineotech.imdb.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import com.promineotech.imdb.models.TitleModel;

@Repository
@Primary
public class JdbcTitleRepository implements TitleRepository {
  //@Autowired
  private NamedParameterJdbcTemplate provider;
  
  public JdbcTitleRepository(NamedParameterJdbcTemplate provider) {
    this.provider = provider;
  }
  
  @Override
  public Stream<TitleModel> all(int limit) {
    String sql = "SELECT title_id, primary_title, start_year "
               + "FROM title "
               + "LIMIT " + limit;
    List<TitleModel> titles = provider.query(sql, new RowMapper<TitleModel>() {
      @Override
      public TitleModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        String id = rs.getString("title_id");
        String name = rs.getString("primary_title");
        int releasedYear = rs.getInt("start_year");
        TitleModel model = new TitleModel(id, name, releasedYear);
        return model;
      }
    });
    
    return titles.stream();
  }

  @Override
  public Optional<TitleModel> get(String id) {
    String sql = "SELECT title_id, primary_title, start_year "
                + "FROM title "
                + "WHERE title_id = :title_id;";
    MapSqlParameterSource parameters = new MapSqlParameterSource();
    parameters.addValue("title_id", id);
                
    List<TitleModel> titles = provider.query(sql, parameters, new RowMapper<TitleModel>() {
      @Override
      public TitleModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TitleModel(rs.getString("title_id"),
                              rs.getString("primary_title"),
                              rs.getInt("start_year"));
      }
    });
    
    if (titles.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(titles.get(0));
  }

  @Override
  public Optional<TitleModel> save(TitleModel input) {
    if (input == null) {
      return Optional.empty();
    }

    return save(input.getId(), input);
  }

  @Override
  public Optional<TitleModel> save(String id, TitleModel input) {
    if (input == null) {
      return Optional.empty();
    }
    if (input.isValid()) {
      Optional<TitleModel> existing = get(id);
      String sql = null;
      if (existing.isEmpty()) {
        sql = "INSERT INTO title (title_id,content_type_id,primary_title,start_year) "
            + "VALUES (:title_id,:content_type_id,:primary_title,:start_year);";
      }
      else {
        sql = "UPDATE title SET title_id = :title_id, "
                             + "content_type_id = :content_type_id, "
                             + "primary_title = :primary_title, "
                             + "start_year = :start_year "
                         + "WHERE title_id = :existing_title_id;"; 
      }
      
      // SQL
      MapSqlParameterSource parameters = new MapSqlParameterSource();
      parameters.addValue("title_id", input.getId());
      parameters.addValue("primary_title", input.getName());
      parameters.addValue("start_year", input.getReleasedYear());
      parameters.addValue("content_type_id", 2);
      parameters.addValue("existing_title_id", id);
      
      int rows = provider.update(sql, parameters);
      if (rows == 1) {
        // Log: Success - Created
        return get(input.getId());
      }
      // Log: Error
    }
    
    return Optional.empty();  }

  @Override
  public Optional<TitleModel> delete(String id) {
    if ((id == null) || (id.isEmpty())) {
      return Optional.empty();
    }
    
    Optional<TitleModel> existing = get(id);
    if (existing.isPresent()) {
      // DELETE
      String sql = "DELETE FROM title WHERE title_id = :title_id;";
      MapSqlParameterSource parameters = new MapSqlParameterSource();
      parameters.addValue("title_id", id);
      
      int rows = provider.update(sql, parameters);
      if (rows == 1) {
        return existing;
      }
    }
    return Optional.empty();
  }
}
