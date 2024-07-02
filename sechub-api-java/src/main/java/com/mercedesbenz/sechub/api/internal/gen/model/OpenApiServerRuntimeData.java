/*
 * SecHub API
 * SecHub API description
 *
 * The version of the OpenAPI document: 0.0.0
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.mercedesbenz.sechub.api.internal.gen.model;

import java.io.Serializable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.StringJoiner;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * OpenApiServerRuntimeData
 */
@JsonPropertyOrder({ OpenApiServerRuntimeData.JSON_PROPERTY_SERVER_VERSION })

public class OpenApiServerRuntimeData implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String JSON_PROPERTY_SERVER_VERSION = "serverVersion";
    private String serverVersion;

    public OpenApiServerRuntimeData() {
    }

    public OpenApiServerRuntimeData serverVersion(String serverVersion) {
        this.serverVersion = serverVersion;
        return this;
    }

    /**
     * The sechub server version.
     *
     * @return serverVersion
     **/
    @javax.annotation.Nullable
    @JsonProperty(JSON_PROPERTY_SERVER_VERSION)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

    public String getServerVersion() {
        return serverVersion;
    }

    @JsonProperty(JSON_PROPERTY_SERVER_VERSION)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }

    /**
     * Return true if this ServerRuntimeData object is equal to o.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OpenApiServerRuntimeData serverRuntimeData = (OpenApiServerRuntimeData) o;
        return Objects.equals(serverVersion, serverRuntimeData.serverVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverVersion);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class OpenApiServerRuntimeData {\n");
        sb.append("    serverVersion: ").append(toIndentedString(serverVersion)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    /**
     * Convert the instance into URL query string.
     *
     * @return URL query string
     */
    public String toUrlQueryString() {
        return toUrlQueryString(null);
    }

    /**
     * Convert the instance into URL query string.
     *
     * @param prefix prefix of the query string
     * @return URL query string
     */
    public String toUrlQueryString(String prefix) {
        String suffix = "";
        String containerSuffix = "";
        String containerPrefix = "";
        if (prefix == null) {
            // style=form, explode=true, e.g. /pet?name=cat&type=manx
            prefix = "";
        } else {
            // deepObject style e.g. /pet?id[name]=cat&id[type]=manx
            prefix = prefix + "[";
            suffix = "]";
            containerSuffix = "]";
            containerPrefix = "[";
        }

        StringJoiner joiner = new StringJoiner("&");

        // add `serverVersion` to the URL query string
        if (getServerVersion() != null) {
            joiner.add(String.format("%sserverVersion%s=%s", prefix, suffix,
                    URLEncoder.encode(String.valueOf(getServerVersion()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
        }

        return joiner.toString();
    }
}
