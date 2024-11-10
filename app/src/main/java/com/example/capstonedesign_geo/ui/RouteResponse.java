package com.example.capstonedesign_geo.ui;


import java.util.List;

public class RouteResponse {
    private Route route;

    public Route getRoute() {
        return route;
    }

    public static class Route {
        private List<RouteItem> traoptimal;

        public List<RouteItem> getTraoptimal() {
            return traoptimal;
        }
    }

    public static class RouteItem {
        private List<RoutePoint> path;

        public List<RoutePoint> getPath() {
            return path;
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
