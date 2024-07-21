package com.flas.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.flas.exception.ResourceNotFoundException;
import com.flas.model.Alert;
import com.flas.model.VehicleData;
import com.flas.repository.AlertRepository;
import com.flas.repository.VehicleDataRepository;

@Service
public class KafkaConsumerService {

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	private VehicleDataRepository vehicleDataRepository;

	@Autowired
	private AlertRepository alertRepository;

	Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

	//This queue is used to store's fuelLevel data
	private Queue<VehicleData> fuelDataQueue = new LinkedList<>();

	//Here we are listening OR consuming the vehicle data which is produced on the topic by producer
	
	@KafkaListener(topics = "VehicleData", groupId = "group_id", containerFactory = "vehicleListener")
	public void listen(VehicleData vehicleData) {
		logger.info("Received : " + vehicleData + " from the VehicleData");

		try {
			
			//saving the vehicle data into mongodb database
			vehicleDataRepository.save(vehicleData);
			
			//checking the fuelLevel is less that 5 or not
			if (vehicleData.getFuelLevel() < 5) {
				fuelDataQueue.add(vehicleData);
				boolean alertCondition = false;
				if (fuelDataQueue.size() > 30) {
					fuelDataQueue.poll();
					//checking the fuelLevel value of all records of queue is less than 5 or not
					alertCondition = fuelDataQueue.stream().allMatch(d -> d.getFuelLevel() < 5);
				}

				//If this condition is true then I'll generate the alert 
				if (alertCondition) {
					Alert alert = new Alert();
					SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
					String formattedDate = sdf.format(new Date());
					alert.setVin(vehicleData.getVIN()); // Use actual VIN
					alert.setMessage("Fuel level is below 5 for more than 30 seconds");
					alert.setTimestamp(formattedDate);

					logger.info("Alert : " + alert + " Generated");
					//save the alert into the mongodb database
					alertRepository.save(alert);

					// Send alert through WebSocket
					template.convertAndSend("/topic/alerts", alert);

				}
			} else {
				fuelDataQueue.clear();
			}
		} catch (DataAccessException e) {
			logger.error("Database error: ", e);
			throw e;
		} catch (IllegalArgumentException e) {
			logger.error("Invalid argument: ", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error processing vehicle data: ", e);
			throw new RuntimeException("Error processing vehicle data", e);
		}
	}

	//This method is used to get all alert from the mongodb database in pages
	public Page<Alert> getAllAlerts(Pageable pageable) {
		try {
			Page<Alert> alerts = alertRepository.findAll(pageable);
			if (alerts.isEmpty()) {
				throw new ResourceNotFoundException("No alerts found");
			}
			return alerts;
		} catch (DataAccessException e) {
			logger.error("Database error: ", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error fetching alerts: ", e);
			throw new RuntimeException("Error fetching alerts", e);
		}
	}

	//This method is used to get all vehicleData from the mongodb database in pages
	public Page<VehicleData> getVehicleDataRecords(Pageable pageable) {
		try {
			Page<VehicleData> vehicleDataRecords = vehicleDataRepository.findAll(pageable);
			if (vehicleDataRecords.isEmpty()) {
				throw new ResourceNotFoundException("No vehicle data records found");
			}
			return vehicleDataRecords;
		} catch (DataAccessException e) {
			logger.error("Database error: ", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error fetching vehicle data records: ", e);
			throw new RuntimeException("Error fetching vehicle data records", e);
		}
	}	

}