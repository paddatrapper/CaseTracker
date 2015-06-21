package com.kritsit.casetracker.server.datalayer;

import static org.mockito.Mockito.*;

import com.kritsit.casetracker.shared.domain.model.Defendant;
import com.kritsit.casetracker.shared.domain.model.Vehicle;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleRepositoryTest extends TestCase {
    private IVehicleRepository vehicleRepo;
    private List<Map<String, String>> vehicleList;

    public VehicleRepositoryTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(VehicleRepositoryTest.class);
    }

    public void setUp() {
        vehicleList = new ArrayList<>();
        Map<String, String> vehicle = new HashMap<>();
        vehicle.put("registration", "CA222-222");
        vehicle.put("make", "Toyota");
        vehicle.put("colour", "Black");
        vehicle.put("isTrailer", "0");
        vehicleList.add(vehicle);
    }
    
    public void testGetVehicles() throws SQLException, RowToModelParseException {
        String id = "9802245849032";
        String sql = "SELECT vehicles.*, defendants.id FROM vehicles INNER JOIN(defendants) WHERE vehicles.owner=defendants.indexID AND defendants.id='" + id + "';";
        IPersistenceService db = mock(IPersistenceService.class);
        when(db.executeQuery(sql)).thenReturn(vehicleList);
        IVehicleRepository vehicleRepo = new VehicleRepository(db);
        Defendant defendant = new Defendant(id, "Bob", "Dylan", "1 address road", "0212221233", "test@testing.co.za", false);

        List<Vehicle> vehicles = vehicleRepo.getVehicles(defendant);
        
        assertTrue(vehicles != null);
        verify(db).executeQuery(sql);
    }

    public void testGetVehicles_Null() throws SQLException, RowToModelParseException {
        String id = "9802245849032";
        String sql = "SELECT vehicles.*, defendants.id FROM vehicles INNER JOIN(defendants) WHERE vehicles.owner=defendants.indexID AND defendants.id='" + id + "';";
        IPersistenceService db = mock(IPersistenceService.class);
        IVehicleRepository vehicleRepo = new VehicleRepository(db);
        Defendant defendant = new Defendant(id, "Bob", "Dylan", "1 address road", "0212221233", "test@testing.co.za", false);

        List<Vehicle> vehicles = vehicleRepo.getVehicles(defendant);

        assertTrue(vehicles == null);
        verify(db).executeQuery(sql);
    }
    
    public void testGetVehicles_Empty() throws SQLException, RowToModelParseException{
        String id = "9802245849032";
        String sql = "SELECT vehicles.*, defendants.id FROM vehicles INNER JOIN(defendants) WHERE vehicles.owner=defendants.indexID AND defendants.id='" + id + "';";
        IPersistenceService db = mock(IPersistenceService.class);
        when(db.executeQuery(sql)).thenReturn(new ArrayList<Map<String, String>>());
        IVehicleRepository vehicleRepo = new VehicleRepository(db);
        Defendant defendant = new Defendant(id, "Bob", "Dylan", "1 address road", "0212221233", "test@testing.co.za", false);

        List<Vehicle> vehicles = vehicleRepo.getVehicles(defendant);
        
        assertTrue(vehicles == null);
        verify(db).executeQuery(sql);
    }
}
