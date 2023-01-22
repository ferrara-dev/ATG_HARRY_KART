package se.atg.service.harrykart.java.domain.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiErrorModel {
    private String errorMessage;
    private String details;


}
