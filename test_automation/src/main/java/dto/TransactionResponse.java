package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionResponse {
    @JsonProperty
    private Long id;

    @JsonProperty
    private String licensePlate;

    @JsonProperty
    private String parkingZone;
}
