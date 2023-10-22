#####################################################################
##                                                                 ##
##  Author: Starling Diaz                                          ##
##  Date: 2021-10-10                                               ##
##  License: Star Tecnologies LLC lincese (https://staracademy.com)##
##  version 1.0                                                    ##
#####################################################################

/*
    This file contains the database schema for the application
*----General Rules ----
* 1. Use underscore_names instead of camelCase
* 2. Use plural names for tables
* 3. Spell out id fields (item_id instead of id)
* 4. Don't use ambiguous column names
* 5. Name foreign keys as columns the same as the columns they reference to
* 6. Use cap for all SQL queries
*/

CREATE SCHEMA IF NOT EXISTS catalogsecure;

SET NAMES 'UTF8MB4';

USE catalogsecure;

DROP TABLE IF EXISTS Users;

CREATE TABLE Users (
    user_id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name      VARCHAR(50) NOT NULL,
    last_name      VARCHAR(50) NOT NULL,
    email           VARCHAR(100) NOT NULL,
    password        VARCHAR(255) DEFAULT NULL,
    address         VARCHAR(255) DEFAULT NULL,
    phone           VARCHAR(30) DEFAULT NULL,
    title           VARCHAR(50) DEFAULT NULL,
    bio             VARCHAR(255) DEFAULT NULL,
    enabled         BOOLEAN DEFAULT FALSE,
    non_locked      BOOLEAN DEFAULT TRUE,
    using_mfa       BOOLEAN DEFAULT FALSE,
    created_at      DATETIME DEFAULT CURRENT_TIMESTAMP,
    img_url         VARCHAR(255) DEFAULT 'https://cdn-icons-png.flaticon.com/512/149/149071.png',
    CONSTRAINT UQ_Users_email UNIQUE (email)
);

DROP TABLE IF EXISTS Roles;

CREATE TABLE Roles (
    role_id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(50) NOT NULL,
    permission     VARCHAR(255) DEFAULT NULL,
    CONSTRAINT UQ_Roles_name UNIQUE (name)
);

DROP TABLE IF EXISTS UserRoles;

CREATE TABLE UserRoles (
    user_role_id    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id         BIGINT UNSIGNED NOT NULL,
    role_id         BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (role_id) REFERENCES Roles (role_id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT UQ_UserRoles_User_Id UNIQUE (user_id)
);

DROP TABLE IF EXISTS Events;

CREATE TABLE Events
(
    event_id    BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type        VARCHAR(50) NOT NULL CHECK(type IN ('LOGIN_ATTEMPT', 'LOGIN_ATTEMPT_FAILURE', 'LOGIN_ATTEMPT_SUCCESS', 'PROFILE_UPDATE', 'PROFILE_PICTURE_UPDATE', 'ROLE_UPDATE', 'ACCOUNT_SETTINGS_UPDATE', 'PASSWORD_UPDATE', 'MFA_UPDATE')),
    description VARCHAR(255) NOT NULL,
    CONSTRAINT UQ_Events_Type UNIQUE (type)
);

DROP TABLE IF EXISTS UserEvents;

CREATE TABLE UserEvents
(
    user_events_id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT UNSIGNED NOT NULL,
    event_id   BIGINT UNSIGNED NOT NULL,
    device     VARCHAR(100) DEFAULT NULL,
    ip_address VARCHAR(100) DEFAULT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (event_id) REFERENCES Events (event_id) ON DELETE RESTRICT ON UPDATE CASCADE
);

DROP TABLE IF EXISTS AccountVerifications;

CREATE TABLE AccountVerifications
  (
    account_verifications_id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id                           BIGINT UNSIGNED NOT NULL,
    url                               VARCHAR(255) NOT NULL,
    -- date     DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT  UQ_AccountVerifications_User_Id UNIQUE (user_id),
    CONSTRAINT  UQ_AccountVerifications_Url UNIQUE (url)
  );

DROP TABLE IF EXISTS ResetPasswordVerifications;

CREATE TABLE ResetPasswordVerifications
  (
    reset_password_verifications_id   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id                           BIGINT UNSIGNED NOT NULL,
    url                               VARCHAR(255) NOT NULL,
    expiration_date                   DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT  UQ_ResetPasswordVerifications_User_Id UNIQUE (user_id),
    CONSTRAINT  UQ_ResetPasswordVerifications_Url UNIQUE (url)
  );

DROP TABLE IF EXISTS TwoFactorVerifications;

CREATE TABLE TwoFactorVerifications
  (
    two_factor_verifications_id       BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id                           BIGINT UNSIGNED NOT NULL,
    code                              VARCHAR(10) NOT NULL,
    expiration_date                   DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT  UQ_TwoFactorVerifications_User_Id UNIQUE (user_id),
    CONSTRAINT  UQ_TwoFactorVerifications_Secret UNIQUE (code)
  );
