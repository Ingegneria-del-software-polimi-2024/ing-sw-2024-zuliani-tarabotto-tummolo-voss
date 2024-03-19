package com.prova.jsonparsing.PointsStrategy;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.prova.jsonparsing.PointsStrategy.Points;

@JsonTypeName("CornersPoints")

public class CornersPoints implements Points {
    private int points;
    @Override
    public int getPoints() {
        return points;
    }
}
