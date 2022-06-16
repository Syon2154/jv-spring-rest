package mate.academy.spring.controller;

import java.util.List;
import java.util.stream.Collectors;
import mate.academy.spring.model.CinemaHall;
import mate.academy.spring.model.dto.CinemaHallRequestDto;
import mate.academy.spring.model.dto.CinemaHallResponseDto;
import mate.academy.spring.service.CinemaHallService;
import mate.academy.spring.service.mapper.CinemaHallMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cinema-halls")
public class CinemaHallController {
    private final CinemaHallService cinemaHallService;
    private final CinemaHallMapper cinemaHallMapper;

    public CinemaHallController(CinemaHallService cinemaHallService,
                                CinemaHallMapper cinemaHallMapper) {
        this.cinemaHallService = cinemaHallService;
        this.cinemaHallMapper = cinemaHallMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CinemaHallResponseDto create(@RequestBody CinemaHallRequestDto requestDto) {
        CinemaHall cinemaHall = cinemaHallMapper.toModel(requestDto);
        cinemaHall = cinemaHallService.add(cinemaHall);
        return cinemaHallMapper.toDto(cinemaHall);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<CinemaHallResponseDto> getAll() {
        return cinemaHallService.getAll().stream()
                .map(cinemaHallMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/inject")
    public String injectCinemaHalls() {
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(100);
        cinemaHall.setDescription("Super hall");
        cinemaHallService.add(cinemaHall);
        return "Cinema Halls are injected!";
    }
}
