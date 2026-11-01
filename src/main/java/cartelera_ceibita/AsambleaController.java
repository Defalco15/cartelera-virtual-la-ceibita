
package cartelera_ceibita;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
public class AsambleaController {

    private final AsambleaRepository asambleaRepository;

    public AsambleaController(AsambleaRepository asambleaRepository) {
        this.asambleaRepository = asambleaRepository;
    }

    @GetMapping("/admin/asambleas")
    public String listarAsambleas(HttpSession session, Model model) {

        if (session.getAttribute("administradorId") == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("asambleas", asambleaRepository.findAll());

        return "asambleas-admin";
    }

    @GetMapping("/admin/asambleas/nueva")
    public String nuevaAsamblea(HttpSession session) {

        if (session.getAttribute("administradorId") == null) {
            return "redirect:/admin/login";
        }

        return "crear-asamblea";
    }

    @PostMapping("/admin/asambleas/guardar")
    public String guardarAsamblea(
            @RequestParam String titulo,
            @RequestParam String descripcion,
            @RequestParam String lugar,
            @RequestParam String fechaAsamblea,
            @RequestParam String fechaFin,
            HttpSession session) {

        if (session.getAttribute("administradorId") == null) {
            return "redirect:/admin/login";
        }

        Asamblea asamblea = new Asamblea();

        asamblea.setTitulo(titulo);
        asamblea.setDescripcion(descripcion);
        asamblea.setLugar(lugar);
        asamblea.setFechaAsamblea(LocalDateTime.parse(fechaAsamblea));
        asamblea.setFechaFin(LocalDateTime.parse(fechaFin));

        asambleaRepository.save(asamblea);

        return "redirect:/admin/asambleas";
    }

    @GetMapping("/admin/asambleas/editar/{id}")
    public String mostrarEdicion(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        if (session.getAttribute("administradorId") == null) {
            return "redirect:/admin/login";
        }

        Asamblea asamblea = asambleaRepository.findById(id).orElse(null);

        if (asamblea == null) {
            return "redirect:/admin/asambleas";
        }

        model.addAttribute("asamblea", asamblea);

        return "editar-asamblea";
    }

    @PostMapping("/admin/asambleas/actualizar")
    public String actualizarAsamblea(
            @RequestParam Long id,
            @RequestParam String titulo,
            @RequestParam String descripcion,
            @RequestParam String lugar,
            @RequestParam String fechaAsamblea,
            @RequestParam String fechaFin,
            HttpSession session) {

        if (session.getAttribute("administradorId") == null) {
            return "redirect:/admin/login";
        }

        Asamblea asamblea = asambleaRepository.findById(id).orElse(null);

        if (asamblea != null) {
            asamblea.setTitulo(titulo);
            asamblea.setDescripcion(descripcion);
            asamblea.setLugar(lugar);
            asamblea.setFechaAsamblea(LocalDateTime.parse(fechaAsamblea));
            asamblea.setFechaFin(LocalDateTime.parse(fechaFin));

            asambleaRepository.save(asamblea);
        }

        return "redirect:/admin/asambleas";
    }

    @PostMapping("/admin/asambleas/eliminar/{id}")
    public String eliminarAsamblea(
            @PathVariable Long id,
            HttpSession session) {

        if (session.getAttribute("administradorId") == null) {
            return "redirect:/admin/login";
        }

        asambleaRepository.deleteById(id);

        return "redirect:/admin/asambleas";
    }
}

