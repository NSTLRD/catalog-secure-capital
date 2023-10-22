/**
 * @author Starling Diaz on 10/7/2023.
 * @Academy StarAcademy
 * @version CatalogSecureCapital 1.0
 * @since 10/7/2023.
 */
package com.starlingdiaz.CatalogSecureCapital.repository;

import com.starlingdiaz.CatalogSecureCapital.model.Role;
import com.starlingdiaz.CatalogSecureCapital.model.User;

import java.util.Collection;

public interface RoleRepository<T extends Role> {
    /*Basic CRUD operations*/

    T create(T data);
    Collection<T> list(int page, int pageSize);
    T get(Long id);
    T update(T data);
    Boolean delete(Long id);

    /*More complex operations*/
    void addRoleToUser(Long userId, String roleName);
    Role getRoleByUserId(Long userId);
    Role getRoleByUserEmail(String email);
    void updateUserRole(Long userId, String roleName);

}