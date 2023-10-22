/**
 * @author Starling Diaz on 10/8/2023.
 * @Academy StarAcademy
 * @version CatalogSecureCapital 1.0
 * @since 10/8/2023.
 */

package com.starlingdiaz.CatalogSecureCapital.query;

public class RoleQuery {

    public static final String  INSERT_ROLE_TO_USER_QUERY = "INSERT INTO UserRoles (user_id,role_id) VALUES (:userId, :roleId)";
    public static final String SELECT_ROLE_BY_NAME_QUERY = "SELECT * FROM Roles WHERE name = :name";
    public static final String SELECT_ROLE_BY_ID_QUERY = "SELECT r.role_id , r.name, r.permission FROM Roles r JOIN UserRoles ur ON ur.role_id = r.role_id JOIN Users u ON u.user_id = ur.user_id WHERE u.user_id = :role_id";
}

//
