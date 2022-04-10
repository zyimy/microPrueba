package com.microservicios.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.microservicios.entity.Bike;
import com.microservicios.repository.BikeRepository;

@Service
public class BikeService {
	
	@Autowired
	private BikeRepository userRepository;
	
	public List<Bike>listaUser(Integer pageNo,Integer pageSize,String sorBy){
	Pageable paging = PageRequest.of(pageNo, pageSize,Sort.by(sorBy));
	Page<Bike>pageResult= userRepository.findAll(paging);
		
		if (pageResult.hasContent()) {
			return pageResult.getContent();
		}else { 
			return new ArrayList<Bike>();
		}
	}
	
	public Bike getById(int id) {
	Optional<Bike> user=userRepository.findById(id);	
		if (user.isPresent()) {
			return user.get();
			
		}else {
			return null;
		}
			
	}
	
	public Bike saveUser(Bike user) {
		
     return userRepository.save(user);    
	
	}
	
	public List<Bike>lista(){
		return userRepository.findAll();
	}
	
	public List<Bike>listById(int userId){
		Optional<Bike>user=userRepository.findById(userId);
		
		if (user.isPresent()) {
			return userRepository.findByUserId(userId);
		}else {
			return null;
		}
		
	}

}
