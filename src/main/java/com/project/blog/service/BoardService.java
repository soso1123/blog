package com.project.blog.service;

import com.project.blog.model.Board;
import com.project.blog.model.User;
import com.project.blog.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void 글쓰기(Board board, User user){ //title과 content만 받음

        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    public Page<Board> 글목록(Pageable pageable){ //페이징 처리

        return boardRepository.findAll(pageable);
    }

    public Page<Board> 글찾기(String searchKeyword, Pageable pageable){

        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }

    public Page<Board> 내글찾기(int userId, Pageable pageable){

        return boardRepository.findByUserId(userId, pageable);
    }

    public Board 글상세보기(Integer id){

        return boardRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다."));
    }

    public void 글삭제(int id){

        boardRepository.deleteById(id);
    }

    @Transactional
    public void 글수정(int id, Board requestBoard){
        Board board = boardRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.")); //영속화 시키기

        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent()); //해당함수 종료 시에(Service가 종료될 때) 트랜잭션이 종료된다. 이 때 더티 체킹이 일어난다.
    }

}
