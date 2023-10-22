/**
 * @author Starling Diaz on 10/7/2023.
 * @Academy StarAcademy
 * @version CatalogSecureCapital 1.0
 * @since 10/7/2023.
 */

package com.starlingdiaz.CatalogSecureCapital.exception;

public class ApiException extends RuntimeException{

    public ApiException(String message) {
        super(message);
    }
}
