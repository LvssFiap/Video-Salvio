package br.com.fiap.gsjava.controllers;

import br.com.fiap.gsjava.models.CityRequest;
import jakarta.validation.ConstraintViolationException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.model.CurrentWeather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clima")
public class WeatherAPI {
    private OWM owm = new OWM("c0f79194c5cdfe6db72de0f4c5e49855");

    Logger log = LoggerFactory.getLogger(WeatherAPI.class);

    @GetMapping
    public CurrentWeather getWeatherByCity(@RequestBody  CityRequest  cityRequest) throws APIException {
        String cityName = cityRequest.getCidade();
        log.info("buscar cidade com o nome: " + cityName);
        return owm.currentWeatherByCityName(cityName);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleValidationExceptions(ConstraintViolationException ex) {
        log.error("Erro de validação: ", ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        log.error("Erro não esperado: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro inesperado. Tente novamente mais tarde.");
    }
}

