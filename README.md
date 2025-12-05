Processus de Login et Génération de Tokens JWT
1. Point d'Entrée - AuthController
   Lorsque l'utilisateur renseigne ses informations de login, nous envoyons la requete de login vers '/login', l'authentification s'effectue via le AuthController qui délègue la logique métier au service approprié :

```java
authService.authenticateAndGenerateToken(userLoginDTO);
```

2. Authentification via AuthenticationManager
   Le service authService procède en deux étapes principales :

Étape 1 : Vérification des identifiants

```java
var authentication = authenticationManager.authenticate(authToken);
```

Cette méthode déclenche le processus d'authentification standard de Spring Security

Elle s'appuie sur le CustomAuthenticationProvider configuré dans l'application

```java
@Override
protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException { 
    /....
}
```
    
Étape 2 : Mise en contexte de sécurité

Une fois l'utilisateur validé, son authentification est stockée dans le contexte de sécurité

```java
SecurityContextHolder.getContext().setAuthentication(authentication);
```

3. Configuration du Fournisseur d'Authentification
   Dans SecurityConfig.class, nous spécifions explicitement l'utilisation de CustomAuthenticationProvider :

```java
@Bean
public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
return http.getSharedObject(AuthenticationManagerBuilder.class)
.authenticationProvider(customAuthenticationProvider)
.build();
}
```

Cette configuration garantit que authenticationManager.authenticate() s'appuie sur notre logique personnalisée.

4. Logique Métier dans CustomAuthenticationProvider
   Le cœur de l'authentification se trouve dans la méthode retrieveUser() du CustomAuthenticationProvider :

Extraction des informations : Récupération du pseudo et code société depuis les credentials

Vérification en base de données : Validation de l'existence de l'utilisateur et de son compte actif

Construction de UserDetails : Création d'un objet contenant les informations et autorisations de l'utilisateur

5. Génération du Token JWT
   Après authentification réussie :

```java
String token = jwtTokenProvider.generateToken(authentication, companyCode);
```
Le token est généré avec les informations de l'utilisateur

Il est retourné au client pour les requêtes futures

 ----

Processus d'Authentification par JWT dans l'Architecture Spring Security
1. Configuration du Filtre JWT
   Dans la classe SecurityConfig, nous positionnons JwtAuthenticationFilter en amont du filtre standard UsernamePasswordAuthenticationFilter :

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    securityProperties.getPublicPatterns().add("/login");
    securityProperties.getPublicPatterns().add("/refresh");
    http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize ->
                    authorize
                            .requestMatchers(securityProperties.getPublicPatterns().toArray(new String[0])).permitAll()
                            .anyRequest().authenticated()
            )
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

    http.addFilterBefore(jwtAuthentificationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}
```
Cette configuration garantit que chaque requête vers une API privée transite systématiquement par notre filtre personnalisé avant tout autre traitement d'authentification.

2. Flux de Vérification du Token
   Lorsqu'un utilisateur authentifié effectue un appel vers un endpoint sécurisé :

Interception par JwtAuthenticationFilter :
Le filtre extrait l'en-tête Authorization de la requête et valide qu'il contient un token au format Bearer {token}.

Validation et Extraction des Données :
Après vérification de la signature et de la validité du token JWT, les informations utilisateur (ex : pseudo, companyCode) sont extraites du payload.

Vérification en Base de Données :
Le filtre interroge la base de données via le service UserDetailsService pour confirmer l'existence de l'utilisateur et récupérer ses autorisations :

```java
UserDetails userDetails = userDetailsService.loadUserByPseudoAndCompanyCode(pseudo, companyCode);
```
Création du Contexte de Sécurité :
Si toutes les vérifications sont satisfaites, un objet Authentication est créé et injecté dans le contexte de sécurité Spring (SecurityContextHolder), autorisant l'accès à la ressource demandée.





