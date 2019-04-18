package com.happy.prj.ctrl;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.happy.prj.kakao.KakaoUserInfo;
import com.happy.prj.token.KakaoAccessToken;

@Controller
public class TestController {
	@RequestMapping(value="/spring.do", method=RequestMethod.GET)
	public String helloCtrl(Model model, String test) {
		model.addAttribute("test", test);
		return "helloSpring";
	}
	
	@RequestMapping(value="/login.do",produces="application/json" ,method=RequestMethod.GET)
	public String kakaoLogin(@RequestParam("code") String code, RedirectAttributes ra , HttpSession session, HttpServletResponse response ,Model model){
		System.out.println("Kakao code : "+ code);
	 
		
		
		 // JsonNode트리형태로 토큰받아온다
        JsonNode jsonToken = KakaoAccessToken.getKakaoAccessToken(code);
        // 여러 json객체 중 access_token을 가져온다
        JsonNode accessToken = jsonToken.get("access_token");
 
        System.out.println("access_token : " + accessToken);
        
        session.setAttribute("accessToken", accessToken);
        // access_token을 통해 사용자 정보 요청
        JsonNode userInfo = KakaoUserInfo.getKakaoUserInfo(accessToken);
 
       


        // Get id
        String id = userInfo.path("id").asText();
        String name = null;
        String email = null;
 
        // 유저정보 카카오에서 가져오기 Get properties
        JsonNode properties = userInfo.path("properties");
        JsonNode kakao_account = userInfo.path("kakao_account");
 
        name = properties.path("nickname").asText();
        email = kakao_account.path("email").asText();
 
        System.out.println("id : " + id);
        System.out.println("name : " + name);
        System.out.println("email : " + email);

        model.addAttribute("code",code);
        model.addAttribute("accessToken",accessToken);
        model.addAttribute("id",id);
        model.addAttribute("name",name);
        model.addAttribute("email",email);

		
		return "login";
	}	
	
	@RequestMapping(value="/logout.do", produces = "application/json")
	public String logout(HttpSession session ,Model model) {
		System.out.println("컨트롤러는 오냐");
		
	// 유저인포 객체선언
		KakaoUserInfo ku = new KakaoUserInfo();
	//노드에 로그아웃한 결과값음 담아줌 매개변수는 세션에 잇는 token을 가져와 문자열로 변환
		JsonNode node = ku.Logout(session.getAttribute("accessToken").toString());
	
		//결과값 출력
		  System.out.println("로그인 후 반환되는 아이디 : " + node.get("id"));
		  
		
		return "logout";
	}
}
