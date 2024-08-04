package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserListDataEntry(
        @JsonProperty("id") int id,
        @JsonProperty("email") String email,
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName,
        @JsonProperty("avatar") String avatar) {
}
