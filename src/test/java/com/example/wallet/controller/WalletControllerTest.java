package com.example.wallet.controller;

import com.example.wallet.model.dto.WalletDto;
import com.example.wallet.service.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext
@ActiveProfiles("test")
class WalletControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    @Autowired
    private ObjectMapper objectMapper;

    WalletDto walletDto;

    @BeforeEach
    public void init() {
        walletDto = new WalletDto();
        walletDto.setWalletId(1l);
        walletDto.setOperationType("DEPOSIT");
        walletDto.setAmount(BigDecimal.TEN);

        WalletController walletController = mock(WalletController.class);
    }

    @Test
    @Order(1)
    void newWallet() throws Exception {
        doNothing().when(walletService).create(isA(WalletDto.class));
        ResultActions result = mockMvc.perform(post("/api/v1/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(walletDto)));

        verify(walletService, times(1)).create(walletDto);

        result.andDo(print())
                .andExpect(status().isOk());

    }


    @Test
    @Order(2)
    void getWalletById() throws Exception {
        given(walletService.findByid(walletDto.getWalletId())).willReturn(walletDto);
        ResultActions result = mockMvc.perform(get("/api/v1/{id}", walletDto.getWalletId()));
        verify(walletService, times(1)).findByid(walletDto.getWalletId());
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.operationType", is(walletDto.getOperationType())))
                .andExpect(jsonPath("$.amount", is(walletDto.getAmount().intValue())));
    }

    @Test
    @Order(3)
    void getAll() {
        WalletDto walletDto2 = new WalletDto();
        walletDto2.setWalletId(2l);
        walletDto2.setOperationType("WITHDRAW");
        walletDto2.setAmount(new BigDecimal(1000));
        List<WalletDto> walletList = Arrays.asList(walletDto, walletDto2);
        when(walletService.findAll()).thenReturn(walletList);
        List<WalletDto> result = walletService.findAll();
        assertEquals(2, result.size());
        assertEquals(walletList, result);
        verify(walletService, times(1)).findAll();
    }

    @Test
    @Order(4)
    void balanceOperation() throws Exception {
        given(walletService.findByid(walletDto.getWalletId())).willReturn(walletDto);
        walletDto.setOperationType("WITHDRAW");
        walletDto.setAmount(new BigDecimal(500));
        doNothing().when(walletService).updateBalance(isA(WalletDto.class));

        ResultActions result = mockMvc.perform(put("/api/v1/wallet", walletDto)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(walletDto)));

        verify(walletService).updateBalance(walletDto);
        result.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(5)
    void deleteWallet() throws Exception {
        willDoNothing().given(walletService).delete(walletDto.getWalletId());
        ResultActions result = mockMvc.perform(delete("/api/v1/{id}", walletDto.getWalletId()));
        verify(walletService).delete(walletDto.getWalletId());
        result.andDo(print())
                .andExpect(status().isOk());
    }
}