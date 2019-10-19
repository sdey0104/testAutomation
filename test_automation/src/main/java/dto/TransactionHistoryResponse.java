package dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionHistoryResponse {

    @JsonProperty
    TransactionHistory[] transactionHistories;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TransactionHistory{
        @JsonProperty
        private Long id;

    }
}
