package ko.or.kosa.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ko.or.kosa.entity.BoardVO;
import ko.or.kosa.entity.SearchVO;
import ko.or.kosa.service.BoardService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	

	@Autowired
	BoardService boardService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/boardList.do", method = RequestMethod.GET)
	public String boardList(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		model.addAttribute("boardList", boardService.boardList());
		
		return "board/boardList";
	}

	@RequestMapping(value="/board/listJSON.do", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
	public Map<String, Object> listJSON (@ModelAttribute("searchVO") SearchVO searchVO, HttpServletRequest request) throws Exception {
		
		long tick = System.nanoTime();
    	//DataTable 검색인자 설정
		searchVO.setOrderColumn(request.getParameter("order[0][column]"));
		searchVO.setDir(request.getParameter("order[0][dir]"));
    
		
		//상위 코드 목록 얻기 
		Map<String, Object> result =  boardService.searchBoardList(searchVO);
		
		tick = System.nanoTime() - tick;
		System.out.println("tick = " + tick);

		return result;
		
	}
	 
	@RequestMapping(value="/board/listJSON2.do", method = RequestMethod.POST)
    @ResponseBody
	public Map<String, Object> listJSON2(@ModelAttribute("searchVO") SearchVO searchVO, HttpServletRequest request) throws Exception {
    	
		//상위 코드 목록 얻기 
		return boardService.searchBoardList2(searchVO);

	}

	@ResponseBody 
	@RequestMapping(value = "/json/boardList.do", method = RequestMethod.GET)
	public JSONObject jsonBoardList(Model model) {
		logger.info("jsonBoardList {}.", boardService.boardList());
		
		JSONObject jsonResult = new JSONObject();
		
		jsonResult.put("boardList", boardService.boardList());
		
		return  jsonResult;
	}

	@ResponseBody 
	@RequestMapping(value = "/json/boardList1.do", method = RequestMethod.GET)
	public List<BoardVO> jsonBoardList1(Model model) {
		logger.info("jsonBoardList {}.", boardService.boardList());
		
		return boardService.boardList();
	}
	
	@ResponseBody 
	@RequestMapping(value = "/json/boardList2.do", method = RequestMethod.POST, headers="Accept=application/json")
	public List<List<Object>> jsonBoardList2(@RequestBody HashMap<String, String> data,
			Model model) {
		
		System.out.println("data=" + data);
		
		List<BoardVO> list = boardService.boardList();
		List<List<Object>> result = new ArrayList<List<Object>>();
		for (BoardVO board : list) {
			List<Object> rowData = new ArrayList<Object>();
			rowData.add(board.getId());
			rowData.add(board.getTitle());
			rowData.add(board.getContent());
			result.add(rowData);
		}
		
		return result;
	}
	
	
	@RequestMapping(value = "/board/insert.do")
	public String insert(Model model) {
		
		boardService.insert();
		
		return "board/insert";
	}
	
}
