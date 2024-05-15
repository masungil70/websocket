package ko.or.kosa.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ko.or.kosa.entity.BoardVO;
import ko.or.kosa.entity.SearchVO;
import ko.or.kosa.mapper.BoardMapper;

@Service("boardService")
public class BoardService {

	@Autowired
	BoardMapper boardMapper;

	public List<BoardVO> boardList() {
		return boardMapper.boardList();
	}

	public Map<String, Object> searchBoardList(SearchVO searchVO) {

		List<List<Object>> array = new ArrayList<List<Object>>();
		int start = searchVO.getStart();
		for (BoardVO item : boardMapper.searchBoardList(searchVO)) {
			List<Object> data = new ArrayList<Object>();
			data.add(String.valueOf(++start));
			data.add(item.getId());
			data.add(item.getTitle());
			data.add(item.getRegister_id());
			data.add(item.getRegister_time());
			array.add(data);
		}

		int recordsTotal = boardMapper.getSearchBoardTotalCount(searchVO);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("recordsTotal", recordsTotal);
		result.put("recordsFiltered", recordsTotal);
		result.put("data", array);

		return result;
	}

	public Map<String, Object> searchBoardList2(SearchVO searchVO) {

		List<List<Object>> array = new ArrayList<List<Object>>();
		int start = searchVO.getStart();
		for (BoardVO item : boardMapper.searchBoardList2(searchVO)) {
			List<Object> data = new ArrayList<Object>();
			data.add(String.valueOf(++start));
			data.add(item.getId());
			data.add(item.getTitle());
			data.add(item.getRegister_id());
			data.add(item.getRegister_time());
			array.add(data);
		}

		int recordsTotal = boardMapper.getSearchBoardTotalCount(searchVO);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("recordsTotal", recordsTotal);
		result.put("recordsFiltered", recordsTotal);
		result.put("data", array);

		return result;
	}

	public void insert() {
		BoardVO board = new BoardVO();
		board.setRegister_id("user1@aaa.com");

		for (int i = 0; i < 50000; i++) {
			board.setTitle("게시물 제목 " + i);
			board.setContent(
					"정부가 외국인 부동산 투기거래 2차 적발에 나선다. 이달 중 기획조사를 마치고 다음 달 조사 결과를 발표할 예정이다. 국토교통부는 외국인의 부동산 투기거래를 차단하기 위해 관련 제도를 정비하고, 국세·관세청 등과 함께 기획조사를 추진한다고 29일 밝혔다. "
							+ i);
			boardMapper.insert(board);
		}
	}
}
