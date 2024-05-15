package ko.or.kosa.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import ko.or.kosa.dto.MessageDTO;

/*ServerEndpoint 어너테이션으로 구성된 클래스는 클라이언트와 서버가 연결된 것을 관리 하는 것, 즉 1:1임
 * 클라이언트에서 받은 메시지를 모든 클라이언트에게 메시지를 전송 하려고 하면
 * 전역 변수로 모든 클라이언트 객체를 관리 해야한다
 * static private Map<String, Session> sessionMap 으로 선언함    
 *   */
@ServerEndpoint("/webSocketExam3/{roomId}/{userId}")
public class WebSocketExam3 {
	static private Map<String, Map<Session, String>> roomMap = new HashMap<>();

	// WebSocket으로 브라우저가 접속하면 요청되는 함수
	@OnOpen
	public void handleOpen(Session session
			, @PathParam("roomId") String roomId /* webSocket 접속시 사용되는 경로 : 채팅방 아이디 */
			, @PathParam("userId") String userId /* webSocket 접속시 사용되는 경로 : 사용자 아이디 */
											) {
		// 콘솔에 접속 로그를 출력한다.
		System.out.println("client is now connected... session.getId() = " + session.getId() + "   roomId = " + roomId + "   userId = " + userId);

		Map<Session, String> sessionMap = roomMap.get(roomId);
		if (sessionMap == null) {
			//채팅방에서 등록할 WebSocket 객체, 사용자 아이디용 객체를 생성한다  
			sessionMap = new HashMap<>();
			
			//채팅방 맴에 채팅방 이름으로 위에서 생성한 객체를 추가한다 
			roomMap.put(roomId, sessionMap);
		}
		
		sessionMap.put(session, userId);
	}

	// WebSocket으로 메시지가 오면 요청되는 함수
	@OnMessage
	public void handleMessage(Session selfSession, String message)  {
		// 메시지 내용을 콘솔에 출력한다.
		System.out.println("receive from client : " + message);
		
		ObjectMapper om = new ObjectMapper();
		MessageDTO messageDTO = null;
		try {
			messageDTO = om.readValue(message, MessageDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// JSON 문자열을 메시지 객체로 변환하여 출력한다
		System.out.println("send to client : " + messageDTO);

		Map<Session, String> sessionMap = roomMap.get(messageDTO.getRoomId());
		///session map 이 존재하지 않으면 리턴한다
		if (sessionMap == null) return;
		
		for (Entry<Session, String> entry : sessionMap.entrySet()) {
  			try {
  				entry.getKey().getBasicRemote().sendText(message);
  			} catch (IOException e) {
  				e.printStackTrace();
  				sessionMap.remove(entry.getKey());
  			}
    }
		 
	}

	// WebSocket과 브라우저가 접속이 끊기면 요청되는 함수
	@OnClose
	public void handleClose(Session session) {
		// 콘솔에 접속 끊김 로그를 출력한다.
		System.out.println("client is now disconnected...");
		System.out.println("handleClose() : session.getId() : " + session.getId());

		for (Entry<String, Map<Session, String>> entry : roomMap.entrySet()) {
			Map<Session, String> sessionMap = entry.getValue();
			if (sessionMap.containsKey(session)) {
				sessionMap.remove(session);
				break;
			}
		}

	}

	// WebSocket과 브라우저 간에 통신 에러가 발생하면 요청되는 함수.
	@OnError
	public void handleError(Throwable t) {
		// 콘솔에 에러를 표시한다.
		t.printStackTrace();
	}
}
