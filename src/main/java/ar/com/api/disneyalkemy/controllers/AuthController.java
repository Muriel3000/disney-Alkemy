package ar.com.api.disneyalkemy.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.api.disneyalkemy.entities.Usuario;
import ar.com.api.disneyalkemy.models.request.*;
import ar.com.api.disneyalkemy.models.response.*;
import ar.com.api.disneyalkemy.security.jwt.JWTTokenUtil;
import ar.com.api.disneyalkemy.services.JWTUserDetailsService;
import ar.com.api.disneyalkemy.services.UsuarioService;;

/**
 * AuthController
 */
@RestController
public class AuthController {

    @Autowired
    UsuarioService usuarioService;

    /*
     * @Autowired private AuthenticationManager authenticationManager;
     */
    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private JWTUserDetailsService userDetailsService;

    // Auth : authentication ->
    @PostMapping("/auth/register")
    public ResponseEntity<RegistrationResponse> postRegisterUser(@RequestBody RegistrationRequest req,
            BindingResult results) {

        RegistrationResponse r = new RegistrationResponse();

        // aca creamos la persona y el usuario a traves del service.

        Usuario usuario = usuarioService.crearUsuario(req.email, req.password);

        r.isOk = true;
        r.message = "Te registraste con exito!";
        r.userId = usuario.getUsuarioId();

        return ResponseEntity.ok(r);

    }

    @PostMapping("/auth/login") 
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest,
            BindingResult results) throws Exception {

        Usuario usuarioLogueado = usuarioService.login(authenticationRequest.username, authenticationRequest.password);

        UserDetails userDetails = usuarioService.getUserAsUserDetail(usuarioLogueado);
        Map<String, Object> claims = usuarioService.getUserClaims(usuarioLogueado);

        String token = jwtTokenUtil.generateToken(userDetails, claims);

        Usuario u = usuarioService.buscarPorUsername(authenticationRequest.username);

        LoginResponse r = new LoginResponse();
        r.id = u.getUsuarioId();
        r.username = authenticationRequest.username;
        r.email = u.getEmail();
        r.token = token;

        return ResponseEntity.ok(r);

    }

}

