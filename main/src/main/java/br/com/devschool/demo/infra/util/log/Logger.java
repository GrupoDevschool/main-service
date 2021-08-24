package br.com.devschool.demo.infra.util.log;

import br.com.devschool.demo.infra.util.log.internal.Logging;
import br.com.devschool.demo.infra.util.log.repository.LogginRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class Logger {
    private final LogginRepository logginRepository;
    private final ObjectMapper objectMapper;


    @SneakyThrows
    public void log(Exception e) {

        Logging log = Logging.builder()
                .name(e.getClass().toString())
                .exception(objectMapper.writeValueAsString(e))
                .date(LocalDateTime.now())
                .build();

        logginRepository.save(log);
    }
}
