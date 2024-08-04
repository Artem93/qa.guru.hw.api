package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserModel(
        @JsonProperty("data") UserData data,
        @JsonProperty("support") UserSupport userSupport) {
}