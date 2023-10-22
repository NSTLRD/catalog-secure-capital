/**
 * @author Starling Diaz on 10/20/2023.
 * @Academy StarAcademy
 * @version CatalogSecureCapital 1.0
 * @since 10/20/2023.
 */

package com.starlingdiaz.CatalogSecureCapital.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequest {
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
}
