package com.multi.withPuppy.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@EnableScheduling
public class UserDAO {

	@Autowired
	SqlSessionTemplate my;

	public int nidUser(String id, String nid) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		params.put("nid", nid);
		return my.update("user.nidUser", params);
	}
	
	public int naverLogin(String nid) {
		int result = 0;
		result = my.selectOne("user.naverLogin", nid);
		return result;
	}
	
	public UserVO naverUser(String nid) {
		UserVO vo = my.selectOne("user.naverUser", nid);
		return vo;
	}
	
	public int kidUser(String id, String kid) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		params.put("kid", kid);
		return my.update("user.kidUser", params);
	}
	
	public int kakaoLogin(String kid) {
		int result = 0;
		result = my.selectOne("user.kakaoLogin", kid);
		return result;
	}
	
	public UserVO kakaoUser(String kid) {
		UserVO vo = my.selectOne("user.kakaoUser", kid);
		return vo;
	}
	
	@Transactional
	public UserVO loginUser(String id, String pw) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		params.put("pw", pw);
		UserVO vo = my.selectOne("user.loginUser", params);
		my.update("user.visitUser", id);
		my.update("user.recentvisitUser", id);
        
		System.out.println("vo>> : " + vo);
		return vo;
	}
	
	@Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    public void updateLevel() {
        // 등급 업데이트 작업 수행
        my.update("user.updateLevel");
    }

	public int idCheck(String id) {
		System.out.println(id);
		return my.selectOne("user.idCheck", id);
	}

	@Autowired
	private JavaMailSenderImpl mailSender;
	private int authNumber;
	// 난수 발생

	public void makeRandomNumber() {
		// 난수의 범위 111111 ~ 999999 (6자리 난수)
		Random r = new Random();
		int checkNum = r.nextInt(888888) + 111111;
		System.out.println("인증번호 : " + checkNum);
		authNumber = checkNum;
	}

	// 이메일 보낼 양식
	public String joinEmail(String email) {
		System.out.println("이메일 양식 설정!");
		makeRandomNumber();
		String setFrom = "emailsender340@gmail.com"; // email-config에 설정한 자신의 이메일 주소를 입력
		String toMail = email;
		String title = "회원 가입 인증 이메일 입니다."; // 이메일 제목
		String content = "홈페이지를 방문해주셔서 감사합니다." + // html 형식으로 작성 !
				"<br><br>" + "인증 번호는 " + authNumber + "입니다." + "<br>" + "해당 인증번호를 인증번호 확인란에 기입하여 주세요."; // 이메일 내용 삽입
		mailSend(setFrom, toMail, title, content);
		return Integer.toString(authNumber);
	}

	// 이메일 전송 메소드
	public void mailSend(String setFrom, String toMail, String title, String content) {
		MimeMessage message = mailSender.createMimeMessage();
		// true 매개값을 전달하면 multipart 형식의 메세지 전달이 가능.문자 인코딩 설정도 가능하다.
		try {
			System.out.println("이메일 전송 성공!");
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			// true 전달 > html 형식으로 전송 , 작성하지 않으면 단순 텍스트로 전달.
			helper.setText(content, true);
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
			System.out.println("이메일 전송 실패!");
		}
	}

	// 닉네임 체크
	public int nicknameCheck(String nickname) {
		System.out.println(nickname);
		return my.selectOne("user.nicknameCheck", nickname);
	}

	// 회원가입
	public int insertUser(UserVO bag) {
		int result = my.insert("user.insertUser", bag);
		System.out.println("다오 insertUser : " + result);
		return result;
	}

	// 비밀번호 체크
	public String pwCheck(String id) {
		return my.selectOne("user.pwCheck", id);
	}

	// 회원탈퇴
	public int deleteUser(String id) {
		return my.update("user.deleteUser", id);
	}

	// 회원수정
	public int updateUser(UserVO bag) {
		int result = my.update("user.updateUser", bag);
		System.out.println("다오 updateUser : " + result);
		return result;
	}

	// 회원 비밀번호 수정
	public int updatePw(String id, String pw) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		params.put("password", pw);
		int result = my.update("user.updatePw", params);
		return result;
	}

	// 내 활동 기록 : 커뮤니티
	public List<UserCommuVO> userCommuList(String id) {
		List<UserCommuVO> commuList = my.selectList("user.userCommuList", id);
		return commuList;
	}
	
	// 내 활동 기록 : 쇼핑 - 주문 리스트
	public List<UserShoppingVO> userShoppingList(String id) {
		List<UserShoppingVO> shoppingList = my.selectList("user.userShoppingList", id);
		
		for(UserShoppingVO usv : shoppingList) {
			usv.setProductList(my.selectList("user.userShoppingProductList", id));
		}
		return shoppingList;
	}
	
	// 내 활동 기록 : 리뷰
	public List<UserReviewVO> userReviewList(String id) {
		List<UserReviewVO> reviewList = my.selectList("user.userReviewList", id);
		return reviewList;
	}
}
