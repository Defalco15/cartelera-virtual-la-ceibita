
package cartelera_ceibita;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@Controller
public class AvisoController {

    private final AvisoRepository avisoRepository;

    public AvisoController(AvisoRepository avisoRepository) {
        this.avisoRepository = avisoRepository;
    }

    @GetMapping("/admin/avisos")
    public String mostrarAvisos(HttpSession session, Model model) {

        if (session.getAttribute("administradorId") == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("avisos", avisoRepository.findAll());

        return "avisos-admin";
    }

    @GetMapping("/admin/avisos/nuevo")
    public String mostrarFormulario(HttpSession session) {

        if (session.getAttribute("administradorId") == null) {
            return "redirect:/admin/login";
        }

        return "crear-aviso";
    }

    @PostMapping("/admin/avisos/guardar")
    public String guardarAviso(
            @RequestParam String titulo,
            @RequestParam String descripcion,
            HttpSession session) {

        if (session.getAttribute("administradorId") == null) {
            return "redirect:/admin/login";
        }

        Aviso aviso = new Aviso();

        aviso.setTitulo(titulo);
        aviso.setDescripcion(descripcion);
        aviso.setFechaPublicacion(LocalDateTime.now());

        avisoRepository.save(aviso);

        return "redirect:/admin/avisos";
    }

    @GetMapping("/admin/avisos/editar/{id}")
    public String mostrarEdicion(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        if (session.getAttribute("administradorId") == null) {
            return "redirect:/admin/login";
        }

        Aviso aviso = avisoRepository.findById(id).orElse(null);

        if (aviso == null) {
            return "redirect:/admin/avisos";
        }

        model.addAttribute("aviso", aviso);

        return "editar-aviso";
    }

    @PostMapping("/admin/avisos/actualizar")
    public String actualizarAviso(
            @RequestParam Long id,
            @RequestParam String titulo,
            @RequestParam String descripcion,
            HttpSession session) {

        if (session.getAttribute("administradorId") == null) {
            return "redirect:/admin/login";
        }

        Aviso aviso = avisoRepository.findById(id).orElse(null);

        if (aviso != null) {
            aviso.setTitulo(titulo);
            aviso.setDescripcion(descripcion);

            avisoRepository.save(aviso);
        }

        return "redirect:/admin/avisos";
    }

    @PostMapping("/admin/avisos/eliminar/{id}")
    public String eliminarAviso(
            @PathVariable Long id,
            HttpSession session) {

        if (session.getAttribute("administradorId") == null) {
            return "redirect:/admin/login";
        }

        avisoRepository.deleteById(id);

        return "redirect:/admin/avisos";
    }
}
