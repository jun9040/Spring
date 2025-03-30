package com.ktdsuniversity.edu.kjh.bbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.kjh.bbs.dao.BoardDao;
import com.ktdsuniversity.edu.kjh.bbs.vo.BoardListVO;
import com.ktdsuniversity.edu.kjh.bbs.vo.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardDao boardDao;

    @Override
    public BoardListVO getAllBoard() {
        BoardListVO boardListVO = new BoardListVO();
        boardListVO.setBoardCnt(boardDao.getBoardAllCount());
        boardListVO.setBoardList(boardDao.getAllBoard());
        return boardListVO;
    }

    @Override
    public boolean createNewBoard(BoardVO boardVO) {
        int createCount = boardDao.createNewBoard(boardVO);
        return createCount > 0;
    }

    @Override
    public BoardVO getOneBoard(int id) {
        // 파라미터로 전달받은 게시글의 조회 수 증가
        // updateCount에는 DB에 업데이트한 게시글의 수를 반환.
        int updateCount = boardDao.increaseViewCount(id);

        if (updateCount == 0) {
            // updateCount가 0이라는 것은 
            // 파라미터로 전달받은 id 값이 DB에 존재하지 않는다는 의미이다.
            // 이 경우, 잘못된 접근입니다. 라고 사용자에게 예외 메시지를 보내준다.
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }

        // 예외가 발생하지 않았다면, 게시글 정보를 조회한다.     
        BoardVO boardVO = boardDao.getOneBoard(id);
        return boardVO;
    }
    
    @Override
    public BoardVO getOneBoard(int id, boolean isIncrease) {
        if (isIncrease) {
            // 파라미터로 전달받은 게시글의 조회 수 증가
            int updateCount = boardDao.increaseViewCount(id);
            if (updateCount == 0) {
                // 존재하지 않는 게시글일 경우 예외 처리
                throw new IllegalArgumentException("잘못된 접근입니다.");
            }
        }

        // 게시글 정보 조회
        BoardVO boardVO = boardDao.getOneBoard(id);
        if (boardVO == null) {
            // 조회된 게시글이 없을 경우 예외 처리
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }

        return boardVO;
    }
    
	@Override
	public boolean updateOneBoard(BoardVO boardVO) {
	// 파라미터로 전달받은 수정된 게시글의 정보로 DB 수정
	// updateCount에는 DB에 업데이트한 게시글의 수를 반환.
		int updateCount = boardDao.updateOneBoard(boardVO);
		return updateCount > 0;
	}

	@Override
	public boolean deleteOneBoard(int id) {
	// 파라미터로 전달받은 id로 게시글을 삭제.
	// deleteCount에는 DB에서 삭제한 게시글의 수를 반환.
		int deleteCount = boardDao.deleteOneBoard(id);
		return deleteCount > 0;
	}

	
}