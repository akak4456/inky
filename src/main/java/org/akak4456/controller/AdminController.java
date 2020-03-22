package org.akak4456.controller;

import org.akak4456.constant.Constants;
import org.akak4456.domain.NotifyBoard;
import org.akak4456.domain.Report;
import org.akak4456.error.IdNotExist;
import org.akak4456.service.AdminService;
import org.akak4456.service.NotifyBoardService;
import org.akak4456.service.ReportService;
import org.akak4456.vo.PageMaker;
import org.akak4456.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.java.Log;

@Controller
@Log
@RequestMapping("/admin/**")
public class AdminController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private NotifyBoardService notifyBoardService;
	//main
	@GetMapping("/main")
	public void main() {
	}
	
	@PostMapping("/upgradeToAdmin/{upgradeId}")
	public ResponseEntity<String> upgradeToAdmin(@PathVariable("upgradeId")String upgradeId){
		try {
			adminService.upgradeToAdminById(upgradeId);
			return new ResponseEntity<>("success",HttpStatus.OK);
		}catch(IdNotExist e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}	
	}
	
	@DeleteMapping("/removeFromAdmin/{removeId}")
	public ResponseEntity<String> removeFromAdmin(@PathVariable("removeId")String removeId){
		try {
			adminService.removeFromAdminById(removeId);
			return new ResponseEntity<>("success",HttpStatus.OK);
		}catch(IdNotExist e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	@PostMapping("/blockUser/{blockId}")
	public ResponseEntity<String> blockUser(@PathVariable("blockId")String blockId){
		try {
			adminService.blockUser(blockId);
			return new ResponseEntity<>("success",HttpStatus.OK);
		}catch(IdNotExist e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	@PostMapping("/unblockUser/{unblockId}")
	public ResponseEntity<String> unblockUser(@PathVariable("unblockId")String blockId){
		try {
			adminService.unblockUser(blockId);
			return new ResponseEntity<>("success",HttpStatus.OK);
		}catch(IdNotExist e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	//report
	@GetMapping("/report/list")
	public void list() {
	}
	@GetMapping("/report/listPage")
	@ResponseBody
	public ResponseEntity<PageMaker<Report>> listPage(PageVO pageVO){
		Page<Report> result = null;
		result = reportService.getListWithPaging(pageVO.makePageable(0, "rno"));
		return new ResponseEntity<>(new PageMaker<Report>(result),HttpStatus.OK);
	}
	@PostMapping("/report/writeReport")
	@ResponseBody
	public ResponseEntity<String> writeReport(@RequestBody Report report){
		reportService.writeReport(report);
		return new ResponseEntity<>(Constants.REPORT_SUCCESS,HttpStatus.OK);
	}
	@DeleteMapping("/report/deleteAll")
	@ResponseBody
	public ResponseEntity<String> deleteAll(){
		reportService.deleteAllReport();
		return new ResponseEntity<>("success",HttpStatus.OK);
	}
	
	
	//notify
	@GetMapping("/notify/write")
	public void write() {
		
	}
	@PostMapping("/notify/write")
	@ResponseBody
	public ResponseEntity<String> writePost(@RequestBody NotifyBoard board) {
		notifyBoardService.save(board);
		return new ResponseEntity<>("success",HttpStatus.OK);
	}
}
