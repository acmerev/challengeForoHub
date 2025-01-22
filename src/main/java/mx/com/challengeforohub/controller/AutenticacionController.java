package mx.com.challengeforohub.controller;

import mx.com.challengeforohub.security.DatosAutenticacionUsuario;
import mx.com.challengeforohub.security.DatosJWTtoken;
import mx.com.challengeforohub.security.TokenService;
import mx.com.challengeforohub.user.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity realizarLogin(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        var token = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.login(),
                datosAutenticacionUsuario.clave());
        System.out.println(token);
        var usuarioAutenticado = authenticationManager.authenticate(token);
        System.out.println(usuarioAutenticado);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        System.out.println(JWTtoken);
        return ResponseEntity.ok(new DatosJWTtoken(JWTtoken));

    }
}
