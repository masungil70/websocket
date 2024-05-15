package ko.or.kosa.service;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ko.or.kosa.entity.BoardVO;
import ko.or.kosa.mapper.BoardMapper;
import ko.or.kosa.service.BoardService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class BoardServiceTest {

	@Autowired
	ApplicationContext ctx;
	@Autowired
	DataSource ds;
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	BoardMapper boardMapper;

	@Test
	public void test() {	
		assertNotNull(ctx);
		assertNotNull(ds);
		assertNotNull(boardMapper);
		List<BoardVO> list = boardMapper.boardList();
		assertNotNull(list);
		
		assertNotEquals(0, list.size());
		list = boardService.boardList();
		assertNotNull(list);
		
		assertNotEquals(0, list.size());
	}

}
