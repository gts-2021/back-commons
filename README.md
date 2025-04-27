Pour le process  de gestion d'authentification :

1- si nous voudrions s'authentifier il faut passer par AuthController, 
  donc celui la va appeler :
authService.authenticateAndGenerateToken(userLoginDTO);

cette méthode va principalement checker l'authentification d'utilisateur via :
var authentication = authenticationManager.authenticate(authToken);
SecurityContextHolder.getContext().setAuthentication(authentication);

la partie : authenticationManager.authenticate(authToken); elle se base
sur la configuration faite par CustomAuthenticationProvider.class 

car nous avons indiquer dans SecurityConfig.class que l'auth doit géré par :
@Bean
public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
return http.getSharedObject(AuthenticationManagerBuilder.class)
.authenticationProvider(customAuthenticationProvider)
.build();
}

2- Pour la vérification d'utilisateur en utilisant le token lors de chaque appel a un api privé on passe toujours par
JwtAuthentificationFilter.class et cel est configuré par : http.addFilterBefore(jwtAuthentificationFilter, UsernamePasswordAuthenticationFilter.class);
dans la classe SecurityConfig.class


