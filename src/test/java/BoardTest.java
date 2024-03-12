
/*
    Junit 4 단위 테스트 수행하기
    1. Junit.jar와 hamcrest-core.jar를 프로젝트에 등록한다.
    2. Junit test case를 추가한다
    3. @Test Annotiation이 추가된 메소드를 작성한다.
    4. [Run As] - [Junit Test]
*/

/*
    Junit4 단위테스트 대상
    1. 영속 계층(Persistance Layer, 즉 DAO)를 테스트한다.
    2. WAS (Tomcat을 의미함)의 개입이 없다 -> WAS가 실행하는 코드는 테스트를 할 수 없다.
        대표적으로 DBCP 설정을 위해 context.xml 파일을 처리하는 과정은 WAS가 담당하므로
        04_dbcp 프로젝트는 Junit4 단위 테스트가 불가능하다.
    3.
*/

/*
    Junit4 주요 Annotiation
    @Test       : 실제 테스트를 수행하는 메소드
    @Before     : @Test 메소드 호출 이전에 동작하ㅏ는 메소드
    @BeforeAll  : Junit TestCase(BoardTest.java) 실행 이전에 동작하는 메소드 (static 처리)
    @After      : @Test 메소드 호출 이후에 동작하는 메소드
    @AfterAll   : Junit Test Case(BoardTest.java) 실행 이후에 동작하는 메소드 (static 처리)

*/

import com.gdu.prj.dao.BoardDao;
import com.gdu.prj.dao.BoardDaoImpl;
import com.gdu.prj.dto.BoardDto;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BoardTest {

  // BoardDao (SingletonPattern)
  private BoardDao boardDao = BoardDaoImpl.getInstance();

  // @Test
  public void 목록개수테스트() {
    int total = boardDao.getBoardCount();
    assertEquals(1000, total);  // total == 1000 이면 테스트 성공
  }

  // @Test
  public void 목록테스트() {

    Map<String, Object> params = Map.of("sort", "DESC",
                                        "begin", 1,
                                        "end", 20);

    List<BoardDto> boardList = boardDao.selectBoardList(params);

    assertEquals(20, boardList.size());

  }

  // @Test
  public void 등록테스트() {

    BoardDto board = BoardDto.builder()
                        .title("테스트제목")
                        .contents("테스트내용")
                      .build();

    int insertCount = boardDao.insertBoard(board);

    assertEquals(1, insertCount);

  }

  public void 수정테스트() {

//    BoardDto board = BoardDto.builder()
//                         .board_no(1)
//                         .title("[수정]테스트제목")
//                         .contents("[수정]테스트내용")
//                       .build();
//
    int updateCount = boardDao.updateBoard(board);

    assertEquals(1, updateCount);

  }

  // @Test
  public void 상세테스트() {

    BoardDto board = boardDao.selectBoardByNo(1);

    assertNotNull(board);  // board 가 null 이 아니면 테스트 성공

  }

  @Test
  public void 삭제테스트() {

    int deleteCount = boardDao.deleteBoard(1);

    assertEquals(1, deleteCount);

  }

}
