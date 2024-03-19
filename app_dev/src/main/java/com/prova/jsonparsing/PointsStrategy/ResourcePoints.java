package com.prova.jsonparsing.PointsStrategy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.prova.jsonparsing.enums.Artifact;
import com.prova.jsonparsing.enums.Element;

@JsonTypeName("ResourcePoints")
public class ResourcePoints implements Points {
    private int points;
    @JsonProperty("element")
    Element element;
    @JsonProperty("artifact")
    Artifact artifact;




    @Override
    public int getPoints() {
        return points;
    }

    public Artifact getArtifacts() {
        return artifact;
    }
}
