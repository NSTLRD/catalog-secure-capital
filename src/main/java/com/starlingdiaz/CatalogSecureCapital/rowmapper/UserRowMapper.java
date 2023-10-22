/**
 * @author Starling Diaz on 10/19/2023.
 * @Academy StarAcademy
 * @version CatalogSecureCapital 1.0
 * @since 10/19/2023.
 */

package com.starlingdiaz.CatalogSecureCapital.rowmapper;

import com.starlingdiaz.CatalogSecureCapital.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("user_id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .address(rs.getString("address"))
                .phone(rs.getString("phone"))
                .title(rs.getString("title"))
                .bio(rs.getString("bio"))
                .imageUrl(rs.getString("img_url"))
                .enabled(rs.getBoolean("enabled"))
                .isUsingMfa(rs.getBoolean("using_mfa"))
                .isNotLocked(rs.getBoolean("non_locked"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .build();
    }
}
