package com.promineotech.imdb.services;

import java.util.List;
import java.util.Optional;
import com.promineotech.imdb.models.TitleModel;

public interface TitleService {
  /**
   * Returns all titles.
   * @param limit The maximum number of titles to return.
   * @return A list of all the titles.
   */
  List<TitleModel> all(int limit);

  /**
   * Gets a title by it's unique identifier.
   * @param id The unique identifier
   * @return The title if found, otherwise returns null.
   */
  TitleModel get(String id);
  
  /**
   * Creates a new title.
   * @param input The new title.
   * @return The new title if successful, otherwise returns null.
   */
  TitleModel create(TitleModel input);
  
  /**
   * Updates an existing title.
   * @param id The unique id to update or change.
   * @param input The updated title information.
   * @return The updated title if successful, otherwise returns null;
   */
  TitleModel update(String id, TitleModel input);
  
  /**
   * Deletes or removes a title. 
   * @param id The unique id of the title to remove.
   * @return The removed title if successful, otherwise returns null.
   */
  TitleModel delete(String id);  
}
