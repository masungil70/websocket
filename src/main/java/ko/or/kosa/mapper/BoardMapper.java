package ko.or.kosa.mapper;

import java.util.List;

import ko.or.kosa.entity.BoardVO;
import ko.or.kosa.entity.SearchVO;

//@Mapper
public interface BoardMapper {
	List<BoardVO> boardList();

	List<BoardVO> searchBoardList(SearchVO searchVO);

	int getSearchBoardTotalCount(SearchVO searchVO);
	
	List<BoardVO> searchBoardList2(SearchVO searchVO);

	void insert(BoardVO boardVO);
}
