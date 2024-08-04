package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "support")
public record UserSupport(
        @JsonProperty("url") String url,
        @JsonProperty("text") String text) {
}
