package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserCreateRequestModel(
        @JsonProperty("name") String name,
        @JsonProperty("job") String job) {
}
