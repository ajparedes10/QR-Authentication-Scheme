package com.ajparedes.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajparedes.data.IDeviceRepository;
import com.ajparedes.model.Device;
import com.ajparedes.service.IDeviceService;

@RestController
@RequestMapping("/devices")
public class RestDeviceController {

	@Autowired
	private IDeviceRepository repo;
	
	@Autowired
	private IDeviceService service;
	
	@GetMapping("/list")
	public List<Device> getAll(){
		return repo.findAll();
	}
	
	@PostMapping("/unlink")
	public ResponseEntity<Object> unlinkDevice(@RequestBody Device dev){
		//TODO verificar si se borra al mandar un usuario diferente al vinculado
		Device d = service.isRegistered(dev.getId());
		if(d!=null && !d.getUsername().equals(dev.getUsername())) {
			return new ResponseEntity<>("You are not authorized to execute this action", HttpStatus.FORBIDDEN);
		}
		repo.delete(dev);
		return new ResponseEntity<>("Device successfully unlinked", HttpStatus.OK);
	}
	
	//llega comando de generar qr en controlador de token	
}