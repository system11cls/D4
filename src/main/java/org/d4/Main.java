package org.d4;

import org.d4.Dto.RouteInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (SQLWorker sqlWorker = new SQLWorker()) {
            var routesId = sqlWorker.getRoutesNames();

            List<RouteInfo> routes = new ArrayList<>();
            for (var id : routesId) {
                routes.add(sqlWorker.getRouteInfo(id));
            }

            for (var route : routes) {
                System.out.println(route);
            }


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}