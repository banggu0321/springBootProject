package com.kosta.myapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.myapp.vo.CarVO;

@RestController
public class SampleRestController {
	
	//@GetMapping("/carlist.do")
	/*public List<CarVO> getCarList(){
		List<CarVO> carlist = new ArrayList<>();
		CarVO c1 = new CarVO("ABC",1000,"red");
		CarVO c2 = new CarVO("DDD",2000,"green");
		CarVO c3 = new CarVO("FFF",3000,"white");
		CarVO c4 = CarVO.builder().model("gg").price(4000).color("white").build();
		
		carlist.add(c1);
		carlist.add(c2);
		carlist.add(c3);
		carlist.add(c4);
		
		return carlist;
	}*/
}
