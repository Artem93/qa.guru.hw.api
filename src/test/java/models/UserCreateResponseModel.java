package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserCreateResponseModel(
        @JsonProperty("name") String name,
        @JsonProperty("job") String job,
        @JsonProperty("id") String id,
        @JsonProperty("createdAt") String createdAt) {
}
