/**
 * @author Starling Diaz on 10/7/2023.
 * @Academy StarAcademy
 * @version CatalogSecureCapital 1.0
 * @since 10/7/2023.
 */
package com.starlingdiaz.CatalogSecureCapital.enumeration;

public enum VerificationType {
    ACCOUNT("ACCOUNT"),
    PASSWORD("PASSWORD");

    private final String type;

    VerificationType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type.toLowerCase();
    }
}
