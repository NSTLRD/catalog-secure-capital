/**
 * @author Starling Diaz on 10/8/2023.
 * @Academy StarAcademy
 * @version CatalogSecureCapital 1.0
 * @since 10/8/2023.
 */

package com.starlingdiaz.CatalogSecureCapital.service.implementation;

import com.starlingdiaz.CatalogSecureCapital.dto.UserDTO;
import com.starlingdiaz.CatalogSecureCapital.dtomapper.UserDTOMapper;
import com.starlingdiaz.CatalogSecureCapital.model.User;
import com.starlingdiaz.CatalogSecureCapital.repository.UserRepository;
import com.starlingdiaz.CatalogSecureCapital.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository<User> userRepository;

    @Override
    public UserDTO createUser(User user) {
        return UserDTOMapper.fromUser(userRepository.create(user));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return UserDTOMapper.fromUser(userRepository.getUserByEmail(email));
    }

    @Override
    public void sendVerificationCode(UserDTO user) {
        userRepository.sendVerificationCode(user);

    }
}
