package com.novahub.javatrain.javaspringbookmanagement.controllers.dto.book;

import com.sun.istack.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnableBookDTO {
    @NotNull
    private boolean enable;
}
