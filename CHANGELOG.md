# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

Created By: BENGUERGOURA Oussama and Hadjersi Mohamed | 01/09/2024


## [Unreleased]

### Added

### Changed

### Deprecated

### Removed

### Fixed

### Security

* Add the possibility to permit public endpoint.

## [1.0.0]

### Added

* Adding validation to `UserLoginDTO`.
* Adding `CustomAuthenticationProvider` to implement retrieveUser method using `pesudo` and `companyCode`.
* Adding `findByPseudoAndCompanyCode` in `CommonUserRepository`.
* Adding `companyCode` to authentication method.
* Adding `AuthService` to handler authentication and token generation.
* Adding `NoArgsConstructor` to `CommonDto`.
* Adding `id` to `CommonRoleDTO`.
* Adding `role` and `email` to `CommonUserDTO`.

### Changed

* Change `UserConstants` to `CommonUserConstants`.
* Renaming `UserDto` to `UserResponse`.
* Authentification checking now from database and not from InMemory saved users.
* Renaming `FunctionalityMapper` and `ModuleMapper` on adding `Common` prefix

### Deprecated

### Removed

 
### Fixed

* Removing BCryptEncoder length fixing auth problem.
 
### Security

 
 
