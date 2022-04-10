package com.microservicios.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservicios.entity.User;
import com.microservicios.feingclients.BikeFeing;
import com.microservicios.feingclients.CarFeing;
import com.microservicios.model.Bike;
import com.microservicios.model.Car;
import com.microservicios.repository.UserRepository;





@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CarFeing carFeing;
	
	@Autowired
	private BikeFeing bikeFeing;
	

	
	public List<User>listaUser(Integer pageNo,Integer pageSize,String sorBy){
	Pageable paging = PageRequest.of(pageNo, pageSize,Sort.by(sorBy));
	Page<User>pageResult= userRepository.findAll(paging);
		
		if (pageResult.hasContent()) {
			return pageResult.getContent();
		}else { 
			return new ArrayList<User>();
		}
	}
	
	public User getById(int id) {
	Optional<User> user=userRepository.findById(id);	
		if (user.isPresent()) {
			return user.get();
			
		}else {
			return null;
		}
			
	}
	
	public User saveUser(User user) {
		
     return userRepository.save(user);    
	
	}
	
	
	
	public List<User>lista(){
		return userRepository.findAll();
	}
	
	
	//Obtener los CAR de el usuario de acuerdo a su ID
	public List<Car>getCars(int userId){
		List<Car>listaCars = restTemplate.getForObject("http://localhost:8002/v1/car/byUser/"+userId, List.class);
		return listaCars;
	}
	
	
	//Obtener las BIKE de el usuario de acuerdo a su ID
	public List<Bike>getBikes(int userId){
		List<Bike>listaBikes = restTemplate.getForObject("http://localhost:8003/v1/bike/byUser/"+userId, List.class);
		
		return listaBikes;
	}
	
	public Car saveCar(int userId, Car car) {
		car.setUserId(userId);
		return carFeing.save(car);
	}
	
	public Bike saveBike(int userId, Bike bike) {
		bike.setUserId(userId);
		return bikeFeing.save(bike);
	}
	
	
	public Map<String, Object> getUsersAndVehicles(int userId) {
		Map<String, Object> result = new HashMap<>();
		User user = userRepository.findById(userId).orElse(null);
		if (user == null) {
			result.put("Mensaje", "No existe el usuario");
			return result;
		}
		result.put("Users", user);
		List<Car> cars = carFeing.listaCars(userId);

		if (cars.isEmpty())
			result.put("Cars", "El usuario: "+userId+" no tiene coches");

		else
			result.put("Cars", cars);
		List<Bike> bikes = bikeFeing.listaBikes(userId);

		if (bikes.isEmpty())
			result.put("Bikes", "El usuario: "+userId+" no tiene bikes");
		else
			result.put("Bike", bikes);

		return result;
	}
	


}
