package dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class LoginResponse {
    @JsonProperty
    private String token;
    @JsonProperty
    private String accountType;
    @JsonProperty
    private String username;
}
