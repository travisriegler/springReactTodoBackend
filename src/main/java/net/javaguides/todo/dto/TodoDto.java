package net.javaguides.todo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {
    //@NotNull will throw the error when the controller receives it if marked with @Valid in the controller
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private boolean completed;
}
