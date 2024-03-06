package com.example.trellobackend.services;

import com.example.trellobackend.dto.BoardResponseDTO;
import com.example.trellobackend.dto.ColumnsDTO;
import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.payload.request.BoardRequest;

import java.util.List;

public interface IBoardService extends IGeneralService<Board> {
    BoardResponseDTO getBoardById(Long boardId);
    BoardResponseDTO createNewBoard(BoardRequest boardRequest);
    List<ColumnsDTO> getAllColumnsDTOByBoardId(Long boardId);
}
