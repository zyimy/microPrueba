package com.microservicios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.entity.Bike;
import com.microservicios.service.BikeService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/bike")
public class BikeController {
	
	@Autowired
	private BikeService userService;
	
	
	@GetMapping("/bikes")
	public ResponseEntity<List<Bike>>listAll(@RequestParam(defaultValue = "0")Integer pageNo,
			@RequestParam(defaultValue = "10")Integer pageSize,
			@RequestParam(defaultValue = "id")String sorBy ){
		
		List<Bike>lista= userService.listaUser(pageNo, pageSize, sorBy);
		
		if (lista.isEmpty()) {
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.ok(lista);
		}
		
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Bike>getIdUser(@PathVariable("id")int id){
		Bike user = userService.getById(id);
		
		if (user==null) {
			
			return ResponseEntity.notFound().build();
			
		}else {
			return ResponseEntity.ok(user);
		}
	}
	
	@PostMapping
	public ResponseEntity<Bike> save(@RequestBody Bike user) {
		Bike user2 = userService.saveUser(user);
		
	  return ResponseEntity.ok(user);
			
		}
	
	
	@GetMapping("/allBikes")
	public ResponseEntity<List<Bike>>listUser()
			{
		
		List<Bike>lista= userService.lista();
		
		if (lista.isEmpty()) {
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.ok(lista);
		}
		
		
	}
	
	@GetMapping("/byUser/{userId}")
	public ResponseEntity<List<Bike>> listaUser(@PathVariable("userId")int userId) {
		List<Bike>lista= userService.listById(userId);
		
		if (lista.isEmpty()) {
		return	ResponseEntity.noContent().build();
			
		}else {
		
			return ResponseEntity.ok(lista);
		}
		
	}
	

}
