package cartelera_ceibita;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    private final AvisoRepository avisoRepository;
    private final ActividadRepository actividadRepository;

    public InicioController(
            AvisoRepository avisoRepository,
            ActividadRepository actividadRepository) {

        this.avisoRepository = avisoRepository;
        this.actividadRepository = actividadRepository;
    }

    @GetMapping("/")
    public String inicio(Model model) {

        model.addAttribute("avisos", avisoRepository.findAll());

        model.addAttribute("actividades",
                actividadRepository.findAll());

        return "index";
    }
}