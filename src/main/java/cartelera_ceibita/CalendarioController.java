package cartelera_ceibita;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
public class CalendarioController {

    private final ActividadRepository actividadRepository;
    private final AsambleaRepository asambleaRepository;

    public CalendarioController(
            ActividadRepository actividadRepository,
            AsambleaRepository asambleaRepository) {

        this.actividadRepository = actividadRepository;
        this.asambleaRepository = asambleaRepository;
    }

    @GetMapping("/api/actividades")
    public List<Actividad> obtenerActividades() {
        return actividadRepository.findAll();
    }

    @GetMapping("/api/asambleas")
    public List<Map<String, Object>> obtenerAsambleas() {

        return asambleaRepository.findAll().stream().map(asamblea -> {

            return Map.<String, Object>of(
                "titulo", asamblea.getTitulo(),
                "descripcion", asamblea.getDescripcion(),
                "lugar", asamblea.getLugar(),
                "fechaAsamblea", asamblea.getFechaAsamblea(),
                "fechaFin", asamblea.getFechaFin()
            );

        }).toList();
    }
}