package com.kritsit.casetracker.server.datalayer;

import com.kritsit.casetracker.shared.domain.model.Defendant;
import com.kritsit.casetracker.shared.domain.model.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VehicleRepository implements IVehicleRepository {
    private final Logger logger = LoggerFactory.getLogger(VehicleRepository.class);
    private final IPersistenceService db;

    public VehicleRepository(IPersistenceService db){
        this.db = db;
    }

    public List<Vehicle> getVehicles(Defendant defendant) throws RowToModelParseException {
        try {
            logger.info("Fetching vehicles for defendant {}", defendant.getName());
            String sql = "SELECT vehicles.*, defendants.id FROM vehicles " +
                "INNER JOIN(defendants) WHERE vehicles.owner=defendants.indexID " +
                "AND defendants.id=?;";
            List<Map<String, String>> rs = db.executeQuery(sql, defendant.getId());

            if(rs == null || rs.isEmpty()) {
                logger.debug("No vehicles found for defendant {}", defendant.getName());
                return null;
            }

            List<Vehicle> vehicles = new ArrayList<>();
            for (Map<String, String> line : rs) {
                String reg = line.get("registration");
                String make = line.get("make");
                String colour = line.get("colour");
                boolean isTrailer = "1".equals(line.get("isTrailer"));
                Vehicle v = new Vehicle(reg, make, colour, isTrailer);
                vehicles.add(v);
            }
            return vehicles;
        } catch(Exception e) {
            logger.error("Error retrieving vehicles for {}", defendant.getName(), e);
            throw new RowToModelParseException("Error retrieving vehicles " +
                    "from database for defendant name: " + defendant.getName(), e);
        }
    }

    public void insertVehicle(Vehicle vehicle, Defendant defendant) throws RowToModelParseException {
        try{
            logger.info("Inserting a vehicle for defendant {}", defendant.getName());
            String isTrailer = (vehicle.isTrailer()) ? "1" : "0";
            String sql = "INSERT INTO vehicles VALUES(?, ( " +
                "SELECT indexID FROM defendants WHERE firstName=? AND lastName=? " +
                " AND address=?), ?, ?, ?);";
 
            db.executeUpdate(sql, vehicle.getRegistration(), defendant.getFirstName(),
                    defendant.getLastName(), defendant.getAddress(), vehicle.getMake(),
                vehicle.getColour(), isTrailer);
        } catch(Exception e){
            logger.error("Error inserting vehicle into the database", e);
            throw new RowToModelParseException("Error inserting values to database", e);
        }
    }
}
