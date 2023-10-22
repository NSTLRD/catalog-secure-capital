/**
 * @author Starling Diaz on 10/7/2023.
 * @Academy StarAcademy
 * @version CatalogSecureCapital 1.0
 * @since 10/7/2023.
 */

package com.starlingdiaz.CatalogSecureCapital.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_DEFAULT)
public class Role {
    private Long id;
    private String name;
    private String permission;
}
