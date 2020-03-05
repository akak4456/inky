package org.akak4456.controller;

import org.akak4456.constant.Constants;
import org.akak4456.domain.Report;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.java.Log;

@Controller
@Log
@RequestMapping("/report/**")
public class ReportController {
	@Autowired
	private ReportService reportService;
	@GetMapping("/list")
	public void list() {
	}
	@GetMapping("/listPage")
	@ResponseBody
	public ResponseEntity<PageMaker<Report>> listPage(PageVO pageVO){
		Page<Report> result = null;
		result = reportService.getListWithPaging(pageVO.makePageable(0, "rno"));
		return new ResponseEntity<>(new PageMaker<Report>(result),HttpStatus.OK);
	}
	@PostMapping("/writeReport")
	@ResponseBody
	public ResponseEntity<String> writeReport(@RequestBody Report report){
		reportService.writeReport(report);
		return new ResponseEntity<>(Constants.REPORT_SUCCESS,HttpStatus.OK);
	}
	@DeleteMapping("/deleteAll")
	@ResponseBody
	public ResponseEntity<String> deleteAll(){
		reportService.deleteAllReport();
		return new ResponseEntity<>("success",HttpStatus.OK);
	}
}
