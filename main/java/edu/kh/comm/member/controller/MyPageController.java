package edu.kh.comm.member.controller;

 
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.comm.member.model.service.MyPageService;
import edu.kh.comm.member.model.vo.Member;

@Controller
@RequestMapping("/member/myPage")
@SessionAttributes({"loginMember"})
public class MyPageController {
	
	@Autowired
	private MyPageService service;

	Logger logger = LoggerFactory.getLogger(MyPageController.class);

	@GetMapping("/info")
	public String info() {
		return "member/myPage-info";
	}
	
	@GetMapping("/changePw")
	public String changePw() {
		return "member/myPage-changePw";
	}
	
	@GetMapping("/secession")
	public String secession() {
		return "member/myPage-secession";
	}
	
	@GetMapping("profile")
	public String profile() {
		return "member/myPage-profile";
	}
	
	@PostMapping("/info")
	public String updateInfo(@ModelAttribute("loginMember") Member loginMember,
							@RequestParam Map<String, Object> paramMap,
							RedirectAttributes ra,
							String[] updateAddress
			) {
		
		String memberAddress = String.join(",,", updateAddress);
		if(memberAddress.equals(",,,,")) memberAddress = null;
		
		paramMap.put("memberNo", loginMember.getMemberNo());
		paramMap.put("memberAddress", memberAddress);
		
		int result = service.updateInfo(paramMap);
		
		String message = null; 
		
		if(result > 0) {
			message = "회원 정보가 수정 되었어요~";
			
			loginMember.setMemberNickname((String)paramMap.get("updateNickname"));
			loginMember.setMemberTel((String)paramMap.get("updateTel"));
			loginMember.setMemberAddress((String)paramMap.get("memberAddress"));
			
		} else {
			message = "회원 정보 수정 실패~";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:info";
	}
	
	
	
	
	
	
	
	
	
	
	
}
