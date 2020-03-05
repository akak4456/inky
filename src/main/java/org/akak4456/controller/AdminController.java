package org.akak4456.controller;

import org.akak4456.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.java.Log;

@Controller
@Log
@RequestMapping("/admin/**")
public class AdminController {
	@Autowired
	private AdminService adminService;
	@GetMapping("/main")
	public void main() {
	}
	
	@PostMapping("/upgradeToAdmin/{upgradeId}")
	public ResponseEntity<String> upgradeToAdmin(@PathVariable("upgradeId")String upgradeId){
		if(!adminService.upgradeToAdminById(upgradeId))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>("success",HttpStatus.OK);
	}
	
	@DeleteMapping("/removeFromAdmin/{removeId}")
	public ResponseEntity<String> removeFromAdmin(@PathVariable("removeId")String removeId){
		if(!adminService.removeFromAdminById(removeId))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>("success",HttpStatus.OK);
	}
	@PostMapping("/blockUser/{blockId}")
	public ResponseEntity<String> blockUser(@PathVariable("blockId")String blockId){
		//log.info("uid..."+uid);
		if(!adminService.blockUser(blockId))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>("success",HttpStatus.OK);
	}
	@PostMapping("/unblockUser/{unblockId}")
	public ResponseEntity<String> unblockUser(@PathVariable("unblockId")String blockId){
		//log.info("uid..."+uid);
		if(!adminService.unblockUser(blockId))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>("success",HttpStatus.OK);
	}
}
