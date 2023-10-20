package codepred.rate.dto;

import codepred.enums.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObj {

    private ResponseStatus code;
    private String message;

}
