package com.example.trellobackend.dto;

import com.example.trellobackend.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String avatarUrl;
    private List<WorkspaceDTO> ownedWorkspaces;
    private List<WorkspaceDTO> memberWorkspaces;
    private List<BoardResponseDTO> ownedBoards;
    private List<BoardResponseDTO> memberBoards;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.avatarUrl = user.getAvatarUrl();
        // Map other fields as needed

        this.ownedWorkspaces = user.getOwnedWorkspaces().stream()
                .map(WorkspaceDTO::new)
                .collect(Collectors.toList());

        this.memberWorkspaces = user.getMemberWorkspaces().stream()
                .map(WorkspaceDTO::new)
                .collect(Collectors.toList());

        this.ownedBoards = user.getOwnerBoards().stream()
                .map(board -> new BoardResponseDTO(
                                board,
                                board.getVisibilities(),
                                board.getColumnOrderIds(),
                                board.getColumns().stream()
                                        .map(column -> new ColumnsDTO(column.getId(), column.getTitle()))
                                        .collect(Collectors.toList())
                        )
                )
                .collect(Collectors.toList());

        this.memberBoards = user.getMemberBoards().stream()
                .map(board -> new BoardResponseDTO(
                                board,
                                board.getVisibilities(),
                                board.getColumnOrderIds(),
                                board.getColumns().stream()
                                        .map(column -> new ColumnsDTO(column.getId(), column.getTitle()))
                                        .collect(Collectors.toList())
                        )
                )
                .collect(Collectors.toList());
    }
}
