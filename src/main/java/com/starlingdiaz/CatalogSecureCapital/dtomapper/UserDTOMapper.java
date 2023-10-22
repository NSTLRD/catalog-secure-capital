/**
 * @author Starling Diaz on 10/8/2023.
 * @Academy StarAcademy
 * @version CatalogSecureCapital 1.0
 * @since 10/8/2023.
 */

package com.starlingdiaz.CatalogSecureCapital.dtomapper;


import com.starlingdiaz.CatalogSecureCapital.dto.UserDTO;
import com.starlingdiaz.CatalogSecureCapital.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {

    //create a method to convert from User to UserDTO
    public static UserDTO fromUser(User user) {
        UserDTO userDTO = new UserDTO();
        //copy all the properties from user to userDTO with BeanUtils and copyProperties
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    //create a method to convert from UserDTO to User
    public static User toUser(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }
}
