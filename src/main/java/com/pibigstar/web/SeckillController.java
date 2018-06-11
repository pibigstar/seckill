package com.pibigstar.web;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pibigstar.domain.Seckill;
import com.pibigstar.dto.Exposer;
import com.pibigstar.dto.SeckillExecution;
import com.pibigstar.dto.SeckillResult;
import com.pibigstar.enums.SeckillStateEnum;
import com.pibigstar.exception.RepeatkillException;
import com.pibigstar.exception.SeckillCloseException;
import com.pibigstar.exception.SeckillException;
import com.pibigstar.service.SeckillSecvice;

@RestController
@RequestMapping("/seckill")
public class SeckillController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillSecvice seckillSercvice;

	@GetMapping(value= {"list"})
	public ModelAndView list(ModelAndView mv) {
		mv.setViewName("index");
		List<Seckill> list = seckillSercvice.getSeckillList();
		mv.addObject("list",list);
		return mv;
	}

	@GetMapping("{seckillId}/detail")
	public ModelAndView detail(@PathVariable("seckillId")Long seckillId,ModelAndView mv) {
		if (seckillId==null) {
			mv.setViewName("redirect:/seckill/list");
			return mv;
		}
		Seckill seckill = seckillSercvice.getOneById(seckillId);
		if (seckill==null) {
			mv.setViewName("forward:/seckill/list");
			return mv;
		}
		mv.addObject("seckill", seckill);
		mv.setViewName("detail");
		return mv;
	}

	@PostMapping("{seckillId}/exposer")
	public SeckillResult<Exposer> exposer(@PathVariable("seckillId")Long seckillId) {
		SeckillResult<Exposer> result = null;
		try {
			Exposer exposer = seckillSercvice.exportSeckillUrl(seckillId);
			result = new SeckillResult<Exposer>(true, exposer);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			result = new SeckillResult<>(true, e.getMessage());
		}
		return result;
	}

	@PostMapping("{seckillId}/{md5}/execution")
	public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId")Long seckillId,
			@CookieValue(value="killPhone",required=false)Long userPhone,
			@PathVariable("md5")String md5){
		
		SeckillResult<SeckillExecution> result = null;

		try {
			SeckillExecution executeSeckill = seckillSercvice.executeSeckill(seckillId, userPhone, md5);
			result = new SeckillResult<>(true,executeSeckill);
		} catch (RepeatkillException e) {
			SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
			result = new SeckillResult<>(true, seckillExecution);
		} catch (SeckillCloseException e) {
			SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.END);
			result = new SeckillResult<>(true, seckillExecution);
		} catch (SeckillException e) {
			SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
			result = new SeckillResult<>(true, seckillExecution);
		}
		return result;
	}
	
	@GetMapping("now")
	public SeckillResult<Long> time(){
		Date now = new Date();
		return new SeckillResult<>(true, now.getTime());
	}

}
