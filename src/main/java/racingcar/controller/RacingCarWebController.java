package racingcar.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import racingcar.domain.Cars;
import racingcar.dto.RacingGameRequestDto;
import racingcar.dto.ResultResponseDto;
import racingcar.service.RacingCarService;
import racingcar.util.RandomNumberGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RacingCarWebController {

    @PostMapping("/plays")
    @ResponseBody
    public ResponseEntity<ResultResponseDto> play(@RequestBody RacingGameRequestDto racingGameRequestDto) {
        List<String> names = Arrays.stream(racingGameRequestDto.getNames().split(","))
                .collect(Collectors.toList());

        RacingCarService racingCarService = new RacingCarService(Cars.of(names), racingGameRequestDto.getCount(), new RandomNumberGenerator());
        racingCarService.move();

        ResultResponseDto resultResponseDto = new ResultResponseDto(racingCarService.getWinners(), racingCarService.getCars());
        return ResponseEntity.ok().body(resultResponseDto);
    }
}
