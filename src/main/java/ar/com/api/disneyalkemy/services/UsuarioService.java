package ar.com.api.disneyalkemy.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import ar.com.api.disneyalkemy.entities.Usuario;
import ar.com.api.disneyalkemy.repositories.UsuarioRepository;
import ar.com.api.disneyalkemy.security.Crypto;
import ar.com.api.disneyalkemy.sistema.comm.EmailService;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository repo;

    @Autowired
    private EmailService emailService;
    
    public Usuario crearUsuario(String email, String password){
       
        Usuario u = new Usuario();
        u.setUsername(email);
        u.setEmail(email);
        u.setPassword(Crypto.encrypt(password, email.toLowerCase()));
        repo.save(u);
        emailService.SendEmail(u.getEmail(), "Registracion exitosa", "Bienvenido, ud ha sido registrado en Disney Alkemy!");

        return u;
    }
        
    public Usuario login(String username, String password) {

        Usuario u = buscarPorUsername(username);
        
        if (u == null || !u.getPassword().equals(Crypto.encrypt(password, u.getEmail().toLowerCase()))) {
            throw new BadCredentialsException("Usuario o contraseña invalida");
        }

        u.setFechaLogin(new Date());
        repo.save(u);
        
        return u;
    }

    public Usuario buscarPorUsername(String username){
        return repo.findByUsername(username);
    }

    public UserDetails getUserAsUserDetail(Usuario usuario) {
        
        UserDetails uDetails;
    
        uDetails = new User(usuario.getUsername(), usuario.getPassword(), getAuthorities(usuario));
    
        return uDetails;
    }

    Set < ? extends GrantedAuthority > getAuthorities(Usuario usuario) {

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        if (usuario.getUsuarioId() != null)
        authorities.add(new SimpleGrantedAuthority("CLAIM_usuarioId_" + usuario.getUsuarioId()));
        
        return authorities;
    }

    public Map<String, Object> getUserClaims(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
    
        if (usuario.getUsuarioId() != null)
          claims.put("usuarioId", usuario.getUsuarioId());
    
        return claims;
    }
}
