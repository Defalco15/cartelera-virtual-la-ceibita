
package cartelera_ceibita;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
public class ActividadController {

    private final ActividadRepository actividadRepository;

    public ActividadController(ActividadRepository actividadRepository) {
        this.actividadRepository = actividadRepository;
    }

    @GetMapping("/admin/actividades")
    public String listarActividades(HttpSession session, Model model) {

        if (session.getAttribute("administradorId") == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("actividades", actividadRepository.findAll());

        return "actividades-admin";
    }

    @GetMapping("/admin/actividades/nueva")
    public String nuevaActividad(HttpSession session) {

        if (session.getAttribute("administradorId") == null) {
            return "redirect:/admin/login";
        }

        return "crear-actividad";
    }

    @PostMapping("/admin/actividades/guardar")
    public String guardarActividad(
            @RequestParam String titulo,
            @RequestParam String descripcion,
            @RequestParam String lugar,
            @RequestParam String fechaActividad,
            @RequestParam String fechaFin,
            HttpSession session) {

        if (session.getAttribute("administradorId") == null) {
            return "redirect:/admin/login";
        }

        Actividad actividad = new Actividad();

        actividad.setTitulo(titulo);
        actividad.setDescripcion(descripcion);
        actividad.setLugar(lugar);
        actividad.setFechaActividad(LocalDateTime.parse(fechaActividad));
        actividad.setFechaFin(LocalDateTime.parse(fechaFin));

        actividadRepository.save(actividad);

        return "redirect:/admin/actividades";
    }

    @GetMapping("/admin/actividades/editar/{id}")
    public String mostrarEdicion(
            @PathVariable Long id,
            HttpSession session,
            Model model) {

        if (session.getAttribute("administradorId") == null) {
            return "redirect:/admin/login";
        }

        Actividad actividad = actividadRepository.findById(id).orElse(null);

        if (actividad == null) {
            return "redirect:/admin/actividades";
        }

        model.addAttribute("actividad", actividad);

        return "editar-actividad";
    }

    @PostMapping("/admin/actividades/actualizar")
    public String actualizarActividad(
            @RequestParam Long id,
            @RequestParam String titulo,
            @RequestParam String descripcion,
            @RequestParam String lugar,
            @RequestParam String fechaActividad,
            @RequestParam String fechaFin,
            HttpSession session) {

        if (session.getAttribute("administradorId") == null) {
            return "redirect:/admin/login";
        }

        Actividad actividad = actividadRepository.findById(id).orElse(null);

        if (actividad != null) {
            actividad.setTitulo(titulo);
            actividad.setDescripcion(descripcion);
            actividad.setLugar(lugar);
            actividad.setFechaActividad(LocalDateTime.parse(fechaActividad));
            actividad.setFechaFin(LocalDateTime.parse(fechaFin));

            actividadRepository.save(actividad);
        }

        return "redirect:/admin/actividades";
    }

    @PostMapping("/admin/actividades/eliminar/{id}")
    public String eliminarActividad(
            @PathVariable Long id,
            HttpSession session) {

        if (session.getAttribute("administradorId") == null) {
            return "redirect:/admin/login";
        }

        actividadRepository.deleteById(id);

        return "redirect:/admin/actividades";
    }
}
