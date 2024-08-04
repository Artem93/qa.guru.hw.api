package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "data")
public record UserData(
        @JsonProperty("id") int id,
        @JsonProperty("email") String email,
        @JsonProperty("first_name") String first_name,
        @JsonProperty("last_name") String last_name,
        @JsonProperty("avatar") String avatar) {
}
