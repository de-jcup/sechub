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
 * OpenApiUserSignup
 */
@JsonPropertyOrder({ OpenApiUserSignup.JSON_PROPERTY_EMAIL_ADDRESS, OpenApiUserSignup.JSON_PROPERTY_API_VERSION, OpenApiUserSignup.JSON_PROPERTY_USER_ID })

public class OpenApiUserSignup implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String JSON_PROPERTY_EMAIL_ADDRESS = "emailAddress";
    private String emailAddress;

    public static final String JSON_PROPERTY_API_VERSION = "apiVersion";
    private String apiVersion;

    public static final String JSON_PROPERTY_USER_ID = "userId";
    private String userId;

    public OpenApiUserSignup() {
    }

    public OpenApiUserSignup emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    /**
     * Email address
     *
     * @return emailAddress
     **/
    @javax.annotation.Nullable
    @JsonProperty(JSON_PROPERTY_EMAIL_ADDRESS)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

    public String getEmailAddress() {
        return emailAddress;
    }

    @JsonProperty(JSON_PROPERTY_EMAIL_ADDRESS)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public OpenApiUserSignup apiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
        return this;
    }

    /**
     * The api version, currently only 1.0 is supported
     *
     * @return apiVersion
     **/
    @javax.annotation.Nullable
    @JsonProperty(JSON_PROPERTY_API_VERSION)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

    public String getApiVersion() {
        return apiVersion;
    }

    @JsonProperty(JSON_PROPERTY_API_VERSION)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public OpenApiUserSignup userId(String userId) {
        this.userId = userId;
        return this;
    }

    /**
     * Wanted userid, the userid must be lowercase only!
     *
     * @return userId
     **/
    @javax.annotation.Nullable
    @JsonProperty(JSON_PROPERTY_USER_ID)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

    public String getUserId() {
        return userId;
    }

    @JsonProperty(JSON_PROPERTY_USER_ID)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Return true if this UserSignup object is equal to o.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OpenApiUserSignup userSignup = (OpenApiUserSignup) o;
        return Objects.equals(emailAddress, userSignup.emailAddress) && Objects.equals(apiVersion, userSignup.apiVersion)
                && Objects.equals(userId, userSignup.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailAddress, apiVersion, userId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class OpenApiUserSignup {\n");
        sb.append("    emailAddress: ").append(toIndentedString(emailAddress)).append("\n");
        sb.append("    apiVersion: ").append(toIndentedString(apiVersion)).append("\n");
        sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
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

        // add `emailAddress` to the URL query string
        if (getEmailAddress() != null) {
            joiner.add(String.format("%semailAddress%s=%s", prefix, suffix,
                    URLEncoder.encode(String.valueOf(getEmailAddress()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
        }

        // add `apiVersion` to the URL query string
        if (getApiVersion() != null) {
            joiner.add(String.format("%sapiVersion%s=%s", prefix, suffix,
                    URLEncoder.encode(String.valueOf(getApiVersion()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
        }

        // add `userId` to the URL query string
        if (getUserId() != null) {
            joiner.add(String.format("%suserId%s=%s", prefix, suffix,
                    URLEncoder.encode(String.valueOf(getUserId()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
        }

        return joiner.toString();
    }
}
