package com.promineotech.imdb.repositories;

import java.util.Optional;
import java.util.stream.Stream;
import com.promineotech.imdb.models.TitleModel;

public interface TitleRepository {
  /**
   * Returns all titles.
   * @param limit The maximum number of titles to return.
   * @return A list of all the titles.
   */
  Stream<TitleModel> all(int limit);

  /**
   * Gets a title by it's unique identifier.
   * @param id The unique identifier
   * @return The title if found, otherwise returns null.
   */
  Optional<TitleModel> get(String id);
  
  /**
   * Creates a new title.
   * @param input The new title.
   * @return The new title if successful, otherwise returns an empty optional.
   */
  Optional<TitleModel> save(TitleModel input);
  
  /**
   * Creates a new title.
   * @param id The existing id of the title to update.
   * @param input The new title.
   * @return The new title if successful, otherwise returns an empty optional.
   */
  Optional<TitleModel> save(String id, TitleModel input);
  
  /**
   * Deletes or removes a title. 
   * @param id The unique id of the title to remove.
   * @return The removed title if successful, otherwise returns an empty optional.
   */
  Optional<TitleModel> delete(String id);
}
