package com.sahana.StudentAttendance.Model.Recgonition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FaceResponse {
    private List<Result> result;

    @JsonProperty("plugins_versions")
    private Map<String, String> pluginsVersions;

    // Getters and setters
    public List<Result> getResult() {
        return result;
    }
    public void setResult(List<Result> result) {
        this.result = result;
    }

    public Map<String, String> getPluginsVersions() {
        return pluginsVersions;
    }

    public void setPluginsVersions(Map<String, String> pluginsVersions) {
        this.pluginsVersions = pluginsVersions;
    }
}
