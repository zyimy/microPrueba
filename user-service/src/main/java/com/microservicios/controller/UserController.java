package com.microservicios.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.entity.User;
import com.microservicios.model.Bike;
import com.microservicios.model.Car;
import com.microservicios.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.vavr.collection.Stream;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/all")
	public ResponseEntity<List<User>> listAll(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sorBy) {

		List<User> lista = userService.listaUser(pageNo, pageSize, sorBy);

		if (lista.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(lista);
		}

	}

	@GetMapping("/id/{id}")
	public ResponseEntity<User> getIdUser(@PathVariable("id") int id) {
		User user = userService.getById(id);

		if (user == null) {

			return ResponseEntity.notFound().build();

		} else {
			return ResponseEntity.ok(user);
		}
	}

	@PostMapping
	public ResponseEntity<User> save(@RequestBody User user) {
		User user2 = userService.saveUser(user);

		return ResponseEntity.ok(user);

	}

	@GetMapping("/allUser")
	public ResponseEntity<List<User>> listUser() {
		List<User> lista = userService.lista();

		if (lista.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(lista);
		}

	}

	@CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackGetCars")
	@GetMapping("/byUserCars/{userId}")
	public ResponseEntity<List<Car>> getCarsAll(@PathVariable("userId") int userId) {
		List<Car> listaCars = userService.getCars(userId);

		if (listaCars.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(listaCars);
		}

	}

	@CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackGetBikes")
	@GetMapping("/byUserBikes/{userId}")
	public ResponseEntity<List<Bike>> getBikesAll(@PathVariable("userId") int userId) {
		List<Bike> listaBikes = userService.getBikes(userId);

		if (listaBikes.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(listaBikes);
		}

	}

	@CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackSaveCar")
	@PostMapping("/savecar/{userId}")
	public ResponseEntity<Car> guardarCar(@PathVariable("userId") int userId, @RequestBody Car car) {

		if (userService.getById(userId) == null) {
			return ResponseEntity.noContent().build();
		} else {

			Car car2 = userService.saveCar(userId, car);

			return ResponseEntity.ok(car2);

		}

	}

	@CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackSaveBike")
	@PostMapping("/savebike/{userId}")
	public ResponseEntity<Bike> guardarCar(@PathVariable("userId") int userId, @RequestBody Bike bike) {

		if (userService.getById(userId) == null) {
			return ResponseEntity.notFound().build();
		} else {

			Bike bike2 = userService.saveBike(userId, bike);

			return ResponseEntity.ok(bike2);

		}

	}

	@CircuitBreaker(name = "allCB", fallbackMethod = "fallBackGetAll")
	@GetMapping("/getAll/{userId}")
	public ResponseEntity<Map<String, Object>> getAllVehicles(@PathVariable("userId") int userId) {
		Map<String, Object> result = userService.getUsersAndVehicles(userId);
		return ResponseEntity.ok(result);

	}

	private ResponseEntity<List<Car>> fallBackGetCars(@PathVariable("userId") int userId, RuntimeException e) {
		
		User user = userService.getById(userId);
		String nombreString =user.getNombre();
		return new ResponseEntity("El usuario" + nombreString + " tiene los coches en el trailer", HttpStatus.OK);

	}

	private ResponseEntity<Car> fallBackSaveCar(@PathVariable("userId") int userId, @RequestBody Car car,
			RuntimeException e) {

		return new ResponseEntity("El usuario" + userId + " No  tiene dinero para coches", HttpStatus.OK);

	}

	private ResponseEntity<List<Bike>> fallBackGetBikes(@PathVariable("userId") int userId, RuntimeException e) {
		
		return new ResponseEntity("El usuario" + userId + " tiene los coches en el trailer", HttpStatus.OK);

	}

	private ResponseEntity<Bike> fallBackSaveBike(@PathVariable("userId") int userId, @RequestBody 	Bike bike,
			RuntimeException e) {

		return new ResponseEntity("El usuario" + userId + " No  tiene dinero para motos", HttpStatus.OK);

	}

	private ResponseEntity<Map<String, Object>> fallBackGetAll(@PathVariable("userId") int userId, RuntimeException e) {
		return new ResponseEntity("El usuario" + userId + " tiene los vehiculos en el taller", HttpStatus.OK);

	}
	
	

}
