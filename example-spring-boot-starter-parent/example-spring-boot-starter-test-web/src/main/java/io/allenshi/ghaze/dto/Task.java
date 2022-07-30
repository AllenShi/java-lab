package io.allenshi.ghaze.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Task {
    private String taskDesc;
}
