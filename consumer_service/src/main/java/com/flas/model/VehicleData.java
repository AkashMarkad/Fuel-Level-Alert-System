package com.flas.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("VehiclesData")
public class VehicleData {

	@Id
	private String id;
	private String VIN;
	private int vehicleSpeed;
    private int engineSpeed;
    private boolean brakeStatus;
    private boolean accelerationStatus;
    private int fuelLevel;
    
    public VehicleData() {}
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
	public String getVIN() {
		return VIN;
	}
	public void setVIN(String VIN) {
		this.VIN = VIN;
	}
	public int getVehicleSpeed() {
		return vehicleSpeed;
	}
	public void setVehicleSpeed(int vehicleSpeed) {
		this.vehicleSpeed = vehicleSpeed;
	}
	public int getEngineSpeed() {
		return engineSpeed;
	}
	public void setEngineSpeed(int engineSpeed) {
		this.engineSpeed = engineSpeed;
	}
	public boolean isBrakeStatus() {
		return brakeStatus;
	}
	public void setBrakeStatus(boolean brakeStatus) {
		this.brakeStatus = brakeStatus;
	}
	public boolean isAccelerationStatus() {
		return accelerationStatus;
	}
	public void setAccelerationStatus(boolean accelerationStatus) {
		this.accelerationStatus = accelerationStatus;
	}
	public int getFuelLevel() {
		return fuelLevel;
	}
	public void setFuelLevel(int fuelLevel) {
		this.fuelLevel = fuelLevel;
	}
	@Override
	public String toString() {
		return "VehicleData [ VIN " +  VIN + " vehicleSpeed=" + vehicleSpeed + ", engineSpeed=" + engineSpeed
				+ ", brakeStatus=" + brakeStatus + ", accelerationStatus=" + accelerationStatus + ", fuelLevel="
				+ fuelLevel + "]";
	}
    
    
}