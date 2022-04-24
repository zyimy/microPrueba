package com.microservicios.feingclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.microservicios.model.Car;

//, url = "http://localhost:8002/v1/car"
@FeignClient(name = "car-service" , url = "http://localhost:8002/v1/car")
public interface CarFeing {
	
	@PostMapping
	Car save(@RequestBody Car car);
	
	@GetMapping("/byUser/{userId}")
	List<Car>listaCars(@PathVariable("userId")int userId);

}
