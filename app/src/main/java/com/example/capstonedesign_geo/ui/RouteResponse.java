package com.example.capstonedesign_geo.ui;


import java.util.List;

public class RouteResponse {
    private Route route;

    public Route getRoute() {
        return route;
    }

    public static class Route {
        private List<RoutePoint> points;

        public List<RoutePoint> getPoints() {
            return points;
        }
    }

    public static class RoutePoint {
        private double latitude;
        private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }
}
