package org.d4;

import org.d4.Dto.FlightInfo;
import org.d4.Dto.RouteInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLWorker implements AutoCloseable {
    private static final String username = "postgres";
    private static final String password = "08112004";
    private static final String host = "jdbc:postgresql://localhost:5432/DPAS";

    private final Connection connection;

    SQLWorker() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        this.connection
                = DriverManager.getConnection(
                host, username, password);
        System.out.println(
                "Connected to PostgreSQL database!");
    }


    public List<String> getRoutesNames() throws SQLException {
        List<String> res = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT Distinct route_no\n" +
                "FROM bookings.routes");
        while (resultSet.next()) {
            res.add(resultSet.getString(1));
        }
        return res;
    }

    public RouteInfo getRouteInfo(String routeId) throws SQLException {
        var flightsId = getFlightsId(routeId);
        List<FlightInfo> res = new ArrayList<>();

        for (var id : flightsId) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Distinct fare_conditions, price\n" +
                    "FROM bookings.segments\n" +
                    "WHERE flight_id = '" + id + "'");

            FlightInfo flightInfo = new FlightInfo(id);
            flightInfo.economyPrice = 0;
            flightInfo.businessPrice = 0;
            flightInfo.comfortPrice = 0;
            var economyCnt = 0;
            var businessCnt = 0;
            var comfortCnt = 0;


            while (resultSet.next()) {
                switch (resultSet.getString("fare_conditions")) {
                    case "Economy" -> {
                        economyCnt++;
                        flightInfo.economyPrice += Double.parseDouble(resultSet.getString("price"));
                    }
                    case "Comfort" -> {
                        comfortCnt++;
                        flightInfo.comfortPrice += Double.parseDouble(resultSet.getString("price"));
                    }
                    case "Business" -> {
                        businessCnt++;
                        flightInfo.businessPrice += Double.parseDouble(resultSet.getString("price"));
                    }
                    default -> {}
                }
            }

            if (economyCnt != 0) flightInfo.economyPrice /= economyCnt;
            if (comfortCnt != 0) flightInfo.comfortPrice /= comfortCnt;
            if (businessCnt != 0) flightInfo.businessPrice /= businessCnt;

            res.add(flightInfo);
        }

        return new RouteInfo(routeId, res);
    }

    private List<String> getFlightsId(String routeId) throws SQLException {
        List<String> flightsID = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT Distinct flight_id\n" +
                "FROM bookings.flights\n" +
                "WHERE route_no = '" + routeId + "'");

        while (resultSet.next()) {
            flightsID.add(resultSet.getString("flight_id"));
        }
        return flightsID;
    }



    @Override
    public void close() throws SQLException {
        this.connection.close();
    }
}
