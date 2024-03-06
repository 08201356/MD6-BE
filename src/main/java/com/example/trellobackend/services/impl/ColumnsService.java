package com.example.trellobackend.services.impl;

import com.example.trellobackend.dto.BoardResponseDTO;
import com.example.trellobackend.dto.ColumnsDTO;
import com.example.trellobackend.models.User;
import com.example.trellobackend.models.board.Board;
import com.example.trellobackend.models.board.Columns;
import com.example.trellobackend.models.workspace.Workspace;
import com.example.trellobackend.payload.request.ColumnRequest;
import com.example.trellobackend.repositories.*;
import com.example.trellobackend.services.IColumsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ColumnsService implements IColumsService {
    @Autowired
    private ColumnsRepository columnsRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Override
    public Iterable<Columns> findAll() {
        return null;
    }

    @Override
    public Optional<Columns> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Columns columns) {

    }

    @Override
    public void remove(Long columnId) {
        Optional<Columns> optionalColumn = columnsRepository.findById(columnId);
        if (optionalColumn.isPresent()) {
            // Nếu tồn tại, xóa cột
            columnsRepository.deleteById(columnId);
        } else {
            // Nếu không tồn tại, có thể xử lý theo ý của bạn, ví dụ: ném một ngoại lệ.
            throw new RuntimeException("Not found column id: " + columnId);
        }
    }

    @Override
    public List<ColumnsDTO> getAllColumns() {
        return columnsRepository.findAll().stream()
                .map(ColumnsDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public BoardResponseDTO createNewColumn(ColumnRequest columnRequest) { Optional<User> userOptional = userRepository.findByEmail(columnRequest.getEmail());
        if (userOptional.isPresent()){
            Optional<Workspace> workspaceOptional = workspaceRepository.findById(columnRequest.getWorkspaceId());
            if (workspaceOptional.isPresent()){
                Optional<Board> boardOptional = boardRepository.findById(columnRequest.getBoardId());
                if (boardOptional.isPresent()) {
                    Board board = boardOptional.get();
                    Columns newColumns = new Columns();
                    newColumns.setTitle(columnRequest.getTitle());
                    newColumns.setBoard(board);
                    columnsRepository.save(newColumns);

                    BoardResponseDTO responseDTO = new BoardResponseDTO();
                    responseDTO.setId(board.getId());
                    responseDTO.setTitle(board.getTitle());
                    responseDTO.setVisibility(board.getVisibilities());

                    List<ColumnsDTO> columnsDTOList = board.getColumns()
                            .stream()
                            .map(ColumnsDTO::fromEntity)
                            .collect(Collectors.toList());

                    responseDTO.setColumns(columnsDTOList);

                    List<Long> columnOrderIds = board.getColumnOrderIds();
                    columnOrderIds.add(newColumns.getId());
                    board.setColumnOrderIds(columnOrderIds);
                    boardRepository.save(board);
                    responseDTO.setColumnOrderIds(columnOrderIds);

                    return responseDTO;
                } else {
                    throw new RuntimeException("Error: Board not found.");
                }
            }else {
                throw new RuntimeException("Error: Workspace not found.");
            }
        }else {
            throw new RuntimeException("Error: User not found.");
        }
    }
}