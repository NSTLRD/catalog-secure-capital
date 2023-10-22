/**
 * @author Starling Diaz on 10/7/2023.
 * @Academy StarAcademy
 * @version CatalogSecureCapital 1.0
 * @since 10/7/2023.
 */

package com.starlingdiaz.CatalogSecureCapital.repository.implementation;

import com.starlingdiaz.CatalogSecureCapital.exception.ApiException;
import com.starlingdiaz.CatalogSecureCapital.model.Role;
import com.starlingdiaz.CatalogSecureCapital.model.User;
import com.starlingdiaz.CatalogSecureCapital.model.UserPrincipal;
import com.starlingdiaz.CatalogSecureCapital.repository.RoleRepository;
import com.starlingdiaz.CatalogSecureCapital.repository.UserRepository;
import com.starlingdiaz.CatalogSecureCapital.rowmapper.UserRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.UUID;

import static com.starlingdiaz.CatalogSecureCapital.enumeration.RoleType.ROLE_USER;
import static com.starlingdiaz.CatalogSecureCapital.enumeration.VerificationType.ACCOUNT;
import static com.starlingdiaz.CatalogSecureCapital.query.UserQuery.*;
import static java.util.Map.of;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User>, UserDetailsService {


    private final NamedParameterJdbcTemplate jdbc;
    private final RoleRepository<Role> roleRepository;
    private final BCryptPasswordEncoder encoder;
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error, Please try again later";



    @Override
    public User create(User user) {
        //check if email is unique
        if(getEmailCount(user.getEmail().trim().toLowerCase()) > 0) throw new ApiException("Email already exists, please try another one.");
        // Save new user
        try{
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = getSqlParameterSource(user);
            jdbc.update(INSERT_USER_QUERY, parameters, holder);
            user.setId(requireNonNull(holder.getKey()).longValue());
            //add role to the user
            roleRepository.addRoleToUser(user.getId(), ROLE_USER.name());
            //send verification url
            String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());
            //save Url in verification table
            jdbc.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY, of("userId", user.getId(), "url", verificationUrl));
            // send email to user with verification url
            //emailSevice.sendVerificationUrl(user.getFirstname(), user.getEmail(), verificationUrl , ACCOUNT);
            user.setEnabled(false);
            user.setNotLocked(true);
            //return  the newly created user
            return user;
            //if any error occurs, throw an exception with proper message
        } catch (Exception exception){
            log.error(exception.getMessage());
            throw new ApiException(INTERNAL_SERVER_ERROR_MESSAGE);
        }
    }


    @Override
    public Collection<User> list(int page, int pageSize) {
        return null;
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    private Integer getEmailCount(String email) {
        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY, of("email", email), Integer.class);
    }

    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", encoder.encode(user.getPassword()));
    }

    private String getVerificationUrl(String key, String type) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/user/verification/" + type + "/verify?key=" + key)
                .toUriString();

    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getUserByEmail(email);
        if(user == null) throw new UsernameNotFoundException("User not found");
        else{
            log.info("User found: {}", email);
            return new UserPrincipal(user, roleRepository.getRoleByUserId(user.getId()).getPermission());
        }
    }

    @Override
    public User getUserByEmail(String email) {
        try {
        User user = jdbc.queryForObject(SELECT_USER_BY_EMAIL_QUERY, of("email", email), new UserRowMapper());
        return user;
        } catch (EmptyResultDataAccessException exception){
            throw new ApiException("No User found with email: " + email + " please try again");
        } catch (Exception exception){
            log.error(exception.getMessage());
            throw new ApiException(INTERNAL_SERVER_ERROR_MESSAGE);
        }
    }
}
