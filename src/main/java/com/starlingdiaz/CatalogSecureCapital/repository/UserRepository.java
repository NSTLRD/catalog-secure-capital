package com.starlingdiaz.CatalogSecureCapital.repository;

import com.starlingdiaz.CatalogSecureCapital.model.User;

import java.util.Collection;

/**
 * @author  Created by starlingdiaz on 10/7/2023.
 * @version CatalogSecureCapital 1.0
 * @since 10/7/2023.
 */
public interface UserRepository<T extends User> {
    /*Basic CRUD operations*/

    T create(T data);
    Collection<T> list(int page, int pageSize);
    T get(Long id);
    T update(T data);
    Boolean delete(Long id);

    /*More complex operations*/
    User getUserByEmail(String email);

}
