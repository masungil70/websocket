package ko.or.kosa.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ChatController {
	
	private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
	
	@RequestMapping(value = "/chat/chat1", method = RequestMethod.GET)
	public String chat1(Model model) {
		return "chat/chat1";
	}

	@RequestMapping(value = "/chat/chat2", method = RequestMethod.GET)
	public String chat2(Model model) {
		
		model.addAttribute("userId", "userId_" + System.currentTimeMillis());
		
		return "chat/chat2";
	}

	@RequestMapping(value = "/chat/chat3", method = RequestMethod.GET)
	public String chat3(Model model) {
		long time = System.currentTimeMillis();
		if (time % 2 == 0) {
			model.addAttribute("roomId", "room1");
		} else {
			model.addAttribute("roomId", "room2");
		}
		
		model.addAttribute("userId", "userId_" + System.currentTimeMillis());
		
		return "chat/chat3";
	}

}
