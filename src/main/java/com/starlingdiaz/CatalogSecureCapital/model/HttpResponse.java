/**
 * @author Starling Diaz on 10/8/2023.
 * @Academy StarAcademy
 * @version CatalogSecureCapital 1.0
 * @since 10/8/2023.
 */

package com.starlingdiaz.CatalogSecureCapital.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
@SuperBuilder
@JsonInclude(NON_DEFAULT)
public class HttpResponse {
    protected String timeStamp;
    protected int statusCode;
    protected HttpStatus status;

    protected String reason;
    protected String message;
    protected String developerMessage;
    protected Map<?, ?> data;
}
