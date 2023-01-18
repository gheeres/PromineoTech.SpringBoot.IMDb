package com.promineotech.imdb.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.promineotech.imdb.models.TitleModel;
import com.promineotech.imdb.repositories.TitleRepository;

@Service
public class DefaultTitleService implements TitleService {
  //@Autowired
  private TitleRepository repository;
  
  public DefaultTitleService(TitleRepository repository) {
    this.repository = repository;
  }
  
  @Override
  public List<TitleModel> all(int limit) {
    if (limit <= 0) {
      return Collections.emptyList();
    }
    if (limit > 1000) {
      limit = 1000;
    }
    
    return repository.all(limit)
                     .collect(Collectors.toList());
  }

  @Override
  public TitleModel get(String id) {
    if ((id == null) || (id.isEmpty())) {
      return null;
    }
    
    Optional<TitleModel> model = repository.get(id);
    if (model.isPresent()) {
      return model.get();
    }
    
    return null;
  }

  @Override
  public TitleModel create(TitleModel input) {
    if (input == null) {
      return null;
    }
    
    if (input.isValid()) {
      Optional<TitleModel> model = repository.save(input);
      if (model.isPresent()) {
        return model.get();
      }
    }

    return null;
  }

  @Override
  public TitleModel update(String id, TitleModel input) {
    TitleModel existing = get(id);
    if (existing == null) {
      return null;
    }
    
    Optional<TitleModel> model = repository.save(id, input);
    if (model.isPresent()) {
      return model.get();
    }
    // Log: Oops
    return null;
  }

  @Override
  public TitleModel delete(String id) {
    if ((id == null) || (id.isEmpty())) {
      return null;
    }
    
    Optional<TitleModel> existing = repository.delete(id);
    if (existing.isPresent()) {
      return existing.get();
    }
    return null;
  }
}
