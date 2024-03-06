package com.example.trellobackend.services;

import com.example.trellobackend.dto.CardDTO;
import com.example.trellobackend.dto.ColumnsDTO;
import com.example.trellobackend.models.board.Card;
import com.example.trellobackend.payload.request.CardRequest;

public interface ICardService extends IGeneralService<Card>{
    ColumnsDTO createNewCard(CardRequest cardRequest);
}