// SPDX-License-Identifier: MIT
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
import java.util.Objects;
import java.util.StringJoiner;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * form login definition
 */
@JsonPropertyOrder({ OpenApiScanJobWebScanLoginForm.JSON_PROPERTY_SCRIPT })

public class OpenApiScanJobWebScanLoginForm implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String JSON_PROPERTY_SCRIPT = "script";
    private OpenApiScanJobWebScanLoginFormScript script;

    public OpenApiScanJobWebScanLoginForm() {
    }

    public OpenApiScanJobWebScanLoginForm script(OpenApiScanJobWebScanLoginFormScript script) {
        this.script = script;
        return this;
    }

    /**
     * Get script
     *
     * @return script
     **/
    @javax.annotation.Nullable
    @JsonProperty(JSON_PROPERTY_SCRIPT)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

    public OpenApiScanJobWebScanLoginFormScript getScript() {
        return script;
    }

    @JsonProperty(JSON_PROPERTY_SCRIPT)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public void setScript(OpenApiScanJobWebScanLoginFormScript script) {
        this.script = script;
    }

    /**
     * Return true if this ScanJob_webScan_login_form object is equal to o.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OpenApiScanJobWebScanLoginForm scanJobWebScanLoginForm = (OpenApiScanJobWebScanLoginForm) o;
        return Objects.equals(script, scanJobWebScanLoginForm.script);
    }

    @Override
    public int hashCode() {
        return Objects.hash(script);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class OpenApiScanJobWebScanLoginForm {\n");
        sb.append("    script: ").append(toIndentedString(script)).append("\n");
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

        // add `script` to the URL query string
        if (getScript() != null) {
            joiner.add(getScript().toUrlQueryString(prefix + "script" + suffix));
        }

        return joiner.toString();
    }
}
