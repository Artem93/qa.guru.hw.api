package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record UserList(
        @JsonProperty("page") int page,
        @JsonProperty("per_page") int perPage,
        @JsonProperty("total") int total,
        @JsonProperty("total_pages") int totalPages,
        @JsonProperty("data") List<UserListDataEntry> data,
        @JsonProperty("support") UserSupport userSupport) {
}
