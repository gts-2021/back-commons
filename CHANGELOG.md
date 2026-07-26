# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

Created By: BENGUERGOURA Oussama and Hadjersi Mohamed | 01/09/2024


## [1.3.4]

### Added

* Adding the attribute `role` for `UserResponse.

### Changed


### Deprecated

### Removed

### Fixed

### Security

## [1.3.4]

### Added

### Changed

* Ignoring pseudo and companyCode  case when user login .

### Deprecated

### Removed

### Fixed

### Security

## [1.3.1]

### Added

* Enable `Cors` configuration.

### Changed

* Ignoring pseudo and companyCode  case when user login .

### Deprecated

### Removed

### Fixed

### Security

## [1.3.0]

### Added

* Adding `AuditEntity` to log user actions.
  - implementing generic toString() which format auditable entity to JSON string.

### Changed

### Deprecated

### Removed

### Fixed

### Security

## [1.2.0]

### Added

### Changed

* Upgrade to `springboot4` and `Java21`.

### Deprecated

### Removed

### Fixed

### Security

## [1.1.5]

### Added

### Changed

* Updating `TOKEN_VALIDITY` & `REFRESH_TOKEN_VALIDITY` values [BACKCOMMONS#53](https://quire.io/w/GTS_Golden_Technology_Solutions/#53)

### Deprecated

### Removed

### Fixed

### Security

## [1.1.5]

### Added

### Changed

* Using `companyCode` as claim when genrating/testing tokens, to ensure that we handle the right user.

### Deprecated

### Removed

### Fixed

* Fixing authentification not using password bug. [BACKCOMMONS#166](https://quire.io/w/GTS_Golden_Technology_Solutions/#166)

### Security

## [1.1.4]

### Added

* Implementing `RefreshToken` aspect [BACKCOMMONS#53](https://quire.io/w/GTS_Golden_Technology_Solutions/#53)

### Changed

### Deprecated

### Removed

### Fixed
* Fixing `ResourceNotFoundException` errorCode [BACKCOMMONS#154](https://quire.io/w/GTS_Golden_Technology_Solutions/#154)

### Security

## [1.1.3]

### Added

* Adding `Reference` entity  [BACKCOMMONS#154](https://quire.io/w/GTS_Golden_Technology_Solutions/#154)
* Adding `ReferenceResponse` & `TranslationResponse` [BACKCOMMONS#154](https://quire.io/w/GTS_Golden_Technology_Solutions/#154)
* Adding `ReferenceMapper` & `TranslationMapper` [BACKCOMMONS#154](https://quire.io/w/GTS_Golden_Technology_Solutions/#154)

### Changed

### Deprecated

### Removed

### Fixed
* Fixing `ResourceNotFoundException` errorCode [BACKCOMMONS#154](https://quire.io/w/GTS_Golden_Technology_Solutions/#154)

### Security

## [1.1.2]

### Added

* Adding `Translation` entity to manage translation for `TranslationBaseEntity`

### Changed

### Deprecated

### Removed

### Fixed

### Security

## [1.1.0]

### Added

* Adding user id in UserDetails.

### Changed

### Deprecated

### Removed

### Fixed

### Security



## [1.0.8]

### Added

### Changed

### Deprecated

### Removed

### Fixed

### Security

* Add the possibility to permit public endpoint.

## [1.0.0]

### Added

* Adding `userId` to `UserResponse` in `AuthService`.
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

 
 
