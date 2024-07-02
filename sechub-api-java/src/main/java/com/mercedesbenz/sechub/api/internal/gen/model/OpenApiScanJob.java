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
 * OpenApiScanJob
 */
@JsonPropertyOrder({ OpenApiScanJob.JSON_PROPERTY_API_VERSION, OpenApiScanJob.JSON_PROPERTY_DATA, OpenApiScanJob.JSON_PROPERTY_SECRET_SCAN,
        OpenApiScanJob.JSON_PROPERTY_LICENSE_SCAN, OpenApiScanJob.JSON_PROPERTY_INFRA_SCAN, OpenApiScanJob.JSON_PROPERTY_CODE_SCAN,
        OpenApiScanJob.JSON_PROPERTY_WEB_SCAN })

public class OpenApiScanJob implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String JSON_PROPERTY_API_VERSION = "apiVersion";
    private String apiVersion;

    public static final String JSON_PROPERTY_DATA = "data";
    private OpenApiScanJobData data;

    public static final String JSON_PROPERTY_SECRET_SCAN = "secretScan";
    private OpenApiScanJobSecretScan secretScan;

    public static final String JSON_PROPERTY_LICENSE_SCAN = "licenseScan";
    private OpenApiScanJobLicenseScan licenseScan;

    public static final String JSON_PROPERTY_INFRA_SCAN = "infraScan";
    private OpenApiScanJobInfraScan infraScan;

    public static final String JSON_PROPERTY_CODE_SCAN = "codeScan";
    private OpenApiScanJobCodeScan codeScan;

    public static final String JSON_PROPERTY_WEB_SCAN = "webScan";
    private OpenApiScanJobWebScan webScan;

    public OpenApiScanJob() {
    }

    public OpenApiScanJob apiVersion(String apiVersion) {
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

    public OpenApiScanJob data(OpenApiScanJobData data) {
        this.data = data;
        return this;
    }

    /**
     * Get data
     *
     * @return data
     **/
    @javax.annotation.Nullable
    @JsonProperty(JSON_PROPERTY_DATA)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

    public OpenApiScanJobData getData() {
        return data;
    }

    @JsonProperty(JSON_PROPERTY_DATA)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public void setData(OpenApiScanJobData data) {
        this.data = data;
    }

    public OpenApiScanJob secretScan(OpenApiScanJobSecretScan secretScan) {
        this.secretScan = secretScan;
        return this;
    }

    /**
     * Get secretScan
     *
     * @return secretScan
     **/
    @javax.annotation.Nullable
    @JsonProperty(JSON_PROPERTY_SECRET_SCAN)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

    public OpenApiScanJobSecretScan getSecretScan() {
        return secretScan;
    }

    @JsonProperty(JSON_PROPERTY_SECRET_SCAN)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public void setSecretScan(OpenApiScanJobSecretScan secretScan) {
        this.secretScan = secretScan;
    }

    public OpenApiScanJob licenseScan(OpenApiScanJobLicenseScan licenseScan) {
        this.licenseScan = licenseScan;
        return this;
    }

    /**
     * Get licenseScan
     *
     * @return licenseScan
     **/
    @javax.annotation.Nullable
    @JsonProperty(JSON_PROPERTY_LICENSE_SCAN)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

    public OpenApiScanJobLicenseScan getLicenseScan() {
        return licenseScan;
    }

    @JsonProperty(JSON_PROPERTY_LICENSE_SCAN)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public void setLicenseScan(OpenApiScanJobLicenseScan licenseScan) {
        this.licenseScan = licenseScan;
    }

    public OpenApiScanJob infraScan(OpenApiScanJobInfraScan infraScan) {
        this.infraScan = infraScan;
        return this;
    }

    /**
     * Get infraScan
     *
     * @return infraScan
     **/
    @javax.annotation.Nullable
    @JsonProperty(JSON_PROPERTY_INFRA_SCAN)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

    public OpenApiScanJobInfraScan getInfraScan() {
        return infraScan;
    }

    @JsonProperty(JSON_PROPERTY_INFRA_SCAN)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public void setInfraScan(OpenApiScanJobInfraScan infraScan) {
        this.infraScan = infraScan;
    }

    public OpenApiScanJob codeScan(OpenApiScanJobCodeScan codeScan) {
        this.codeScan = codeScan;
        return this;
    }

    /**
     * Get codeScan
     *
     * @return codeScan
     **/
    @javax.annotation.Nullable
    @JsonProperty(JSON_PROPERTY_CODE_SCAN)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

    public OpenApiScanJobCodeScan getCodeScan() {
        return codeScan;
    }

    @JsonProperty(JSON_PROPERTY_CODE_SCAN)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public void setCodeScan(OpenApiScanJobCodeScan codeScan) {
        this.codeScan = codeScan;
    }

    public OpenApiScanJob webScan(OpenApiScanJobWebScan webScan) {
        this.webScan = webScan;
        return this;
    }

    /**
     * Get webScan
     *
     * @return webScan
     **/
    @javax.annotation.Nullable
    @JsonProperty(JSON_PROPERTY_WEB_SCAN)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

    public OpenApiScanJobWebScan getWebScan() {
        return webScan;
    }

    @JsonProperty(JSON_PROPERTY_WEB_SCAN)
    @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
    public void setWebScan(OpenApiScanJobWebScan webScan) {
        this.webScan = webScan;
    }

    /**
     * Return true if this ScanJob object is equal to o.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OpenApiScanJob scanJob = (OpenApiScanJob) o;
        return Objects.equals(apiVersion, scanJob.apiVersion) && Objects.equals(data, scanJob.data) && Objects.equals(secretScan, scanJob.secretScan)
                && Objects.equals(licenseScan, scanJob.licenseScan) && Objects.equals(infraScan, scanJob.infraScan)
                && Objects.equals(codeScan, scanJob.codeScan) && Objects.equals(webScan, scanJob.webScan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiVersion, data, secretScan, licenseScan, infraScan, codeScan, webScan);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class OpenApiScanJob {\n");
        sb.append("    apiVersion: ").append(toIndentedString(apiVersion)).append("\n");
        sb.append("    data: ").append(toIndentedString(data)).append("\n");
        sb.append("    secretScan: ").append(toIndentedString(secretScan)).append("\n");
        sb.append("    licenseScan: ").append(toIndentedString(licenseScan)).append("\n");
        sb.append("    infraScan: ").append(toIndentedString(infraScan)).append("\n");
        sb.append("    codeScan: ").append(toIndentedString(codeScan)).append("\n");
        sb.append("    webScan: ").append(toIndentedString(webScan)).append("\n");
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

        // add `apiVersion` to the URL query string
        if (getApiVersion() != null) {
            joiner.add(String.format("%sapiVersion%s=%s", prefix, suffix,
                    URLEncoder.encode(String.valueOf(getApiVersion()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
        }

        // add `data` to the URL query string
        if (getData() != null) {
            joiner.add(getData().toUrlQueryString(prefix + "data" + suffix));
        }

        // add `secretScan` to the URL query string
        if (getSecretScan() != null) {
            joiner.add(getSecretScan().toUrlQueryString(prefix + "secretScan" + suffix));
        }

        // add `licenseScan` to the URL query string
        if (getLicenseScan() != null) {
            joiner.add(getLicenseScan().toUrlQueryString(prefix + "licenseScan" + suffix));
        }

        // add `infraScan` to the URL query string
        if (getInfraScan() != null) {
            joiner.add(getInfraScan().toUrlQueryString(prefix + "infraScan" + suffix));
        }

        // add `codeScan` to the URL query string
        if (getCodeScan() != null) {
            joiner.add(getCodeScan().toUrlQueryString(prefix + "codeScan" + suffix));
        }

        // add `webScan` to the URL query string
        if (getWebScan() != null) {
            joiner.add(getWebScan().toUrlQueryString(prefix + "webScan" + suffix));
        }

        return joiner.toString();
    }
}
