/**
 * @author Starling Diaz on 10/8/2023.
 * @Academy StarAcademy
 * @version CatalogSecureCapital 1.0
 * @since 10/8/2023.
 */

package com.starlingdiaz.CatalogSecureCapital.service;

import com.starlingdiaz.CatalogSecureCapital.dto.UserDTO;
import com.starlingdiaz.CatalogSecureCapital.model.User;

public interface UserService {

    UserDTO createUser(User user);
    UserDTO getUserByEmail(String email);

    void sendVerificationCode(UserDTO user);
}
