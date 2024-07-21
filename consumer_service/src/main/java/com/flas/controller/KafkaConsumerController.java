package com.flas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.flas.model.Alert;
import com.flas.model.VehicleData;
import com.flas.service.KafkaConsumerService;



@Controller
public class KafkaConsumerController {

	@Autowired
	private KafkaConsumerService kafkaConsumerService;
	
	/*
	 * Endpoint for homepage
	 */
	@GetMapping("/homepage")
	public String homePage() {
		return "homepage";
	}

	/*
	 * Endpoint for vehicledata page
	 * Here we are fething all the vehicle data record from database and showing on the vehicledata page in pages
	 */
	@GetMapping("/vehicledata")
	public String getAllVehicleDataRecords(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 9; // Set the page size here
        Page<VehicleData> vehicleDataPage = kafkaConsumerService.getVehicleDataRecords(PageRequest.of(page, pageSize));

        model.addAttribute("items", vehicleDataPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", vehicleDataPage.getTotalPages());

        return "vehicledata";
    }
	
	/*
	 * Endpoint for alertdata page
	 * Here we are fething all the alert data record from database and showing on the alert page in pages
	 */
	@GetMapping("/alertData")
	public String getAlerts(Model model, @RequestParam(defaultValue = "0") int page){
		int pageSize = 9; // Set the page size here
		Page<Alert> alertDataPage = kafkaConsumerService.getAllAlerts(PageRequest.of(page, pageSize));
		model.addAttribute("items", alertDataPage.getContent());
		model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", alertDataPage.getTotalPages());
		return "alertData";
	}
	
	/*
	 * This method is used to send alert to websocket 
	 */
	@MessageMapping("/alert")
    @SendTo("/topic/alerts")
    public Alert sendAlert(Alert alert) throws Exception {
        return alert;
    }

}