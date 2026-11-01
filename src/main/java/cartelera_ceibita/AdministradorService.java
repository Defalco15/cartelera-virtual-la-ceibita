
package cartelera_ceibita;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class AdministradorService {

    private final AdministradorRepository administradorRepository;

    private final BCryptPasswordEncoder codificador;

    public AdministradorService(AdministradorRepository administradorRepository) {
        this.administradorRepository = administradorRepository;
        this.codificador = new BCryptPasswordEncoder();
    }

    public Administrador registrarAdministrador(String nombre, String usuario, String contrasena) {

        if (administradorRepository.existsByUsuario(usuario)) {
            throw new IllegalArgumentException("Ese usuario ya existe");
        }

        Administrador administrador = new Administrador();

        administrador.setNombre(nombre);
        administrador.setUsuario(usuario);
        administrador.setContrasena(codificador.encode(contrasena));

        return administradorRepository.save(administrador);
    }

    public boolean verificarContrasena(String contrasena, String contrasenaGuardada) {
        return codificador.matches(contrasena, contrasenaGuardada);
    }
}
