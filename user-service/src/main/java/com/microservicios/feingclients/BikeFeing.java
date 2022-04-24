package com.microservicios.feingclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.microservicios.model.Bike;

//, url = "http://localhost:8003/v1/bike"
@FeignClient(name = "bike-service" , url = "http://localhost:8003/v1/bike")
public interface BikeFeing {
	
	@PostMapping
	Bike save(@RequestBody Bike bike);
	
	@GetMapping("/byUser/{userId}")
	List<Bike>listaBikes(@PathVariable("userId")int userId);

}
