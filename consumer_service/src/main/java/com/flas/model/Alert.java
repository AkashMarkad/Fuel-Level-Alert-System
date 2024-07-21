package com.flas.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Alerts")
public class Alert {

	@Id
	private String id;
	private String VIN;
	private String message;
	private String timestamp;

	public Alert() {
	}
	
	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
	public String getVIN() {
		return VIN;
	}

	public void setVin(String VIN) {
		this.VIN = VIN;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Alert [  VIN=" + VIN + ", message=" + message + ", timestamp=" + timestamp + "]";
	}
}