
package cartelera_ceibita;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    private final AdministradorService administradorService;
    private final AdministradorRepository administradorRepository;

    public AdminController(AdministradorService administradorService,
                           AdministradorRepository administradorRepository) {
        this.administradorService = administradorService;
        this.administradorRepository = administradorRepository;
    }

    @GetMapping("/admin/login")
    public String mostrarLogin() {
        if (administradorRepository.count() == 0) {
            return "redirect:/admin/crear-inicial";
        }
        return "login";
    }

    @GetMapping("/admin/crear-inicial")
    public String mostrarRegistroInicial() {
        if (administradorRepository.count() > 0) {
            return "redirect:/admin/login";
        }
        return "registro-admin";
    }

    @PostMapping("/admin/crear-inicial")
    public String crearAdministradorInicial(
            @RequestParam String nombre,
            @RequestParam String usuario,
            @RequestParam String contrasena,
            Model model) {

        if (administradorRepository.count() > 0) {
            return "redirect:/admin/login";
        }

        if (contrasena.length() < 8) {
            model.addAttribute("error", "La contraseña debe tener al menos 8 caracteres.");
            return "registro-admin";
        }

        if (usuario.trim().isEmpty() || nombre.trim().isEmpty()) {
            model.addAttribute("error", "Completa todos los campos.");
            return "registro-admin";
        }

        administradorService.registrarAdministrador(
                nombre.trim(), usuario.trim(), contrasena);

        return "redirect:/admin/login";
    }

    @PostMapping("/admin/login")
    public String iniciarSesion(
            @RequestParam String usuario,
            @RequestParam String contrasena,
            Model model,
            HttpSession session) {

        Administrador administrador = administradorRepository
                .findByUsuario(usuario)
                .orElse(null);

        if (administrador != null &&
            administradorService.verificarContrasena(
                contrasena, administrador.getContrasena())) {

            session.setAttribute("administradorId", administrador.getId());
           

            return "redirect:/admin/panel";
        }

        model.addAttribute("error", true);
        return "login";
    }

    @GetMapping("/admin/panel")
    public String mostrarPanel(HttpSession session, Model model) {

        Long administradorId =
                (Long) session.getAttribute("administradorId");

        if (administradorId == null) {
            return "redirect:/admin/login";
        }

        Administrador administrador = administradorRepository
                .findById(administradorId)
                .orElse(null);

        if (administrador == null) {
            session.invalidate();
            return "redirect:/admin/login";
        }

        model.addAttribute("administrador", administrador);
        return "admin-panel";
    }

    @GetMapping("/admin/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }
}
