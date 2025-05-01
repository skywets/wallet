package com.example.wallet.controller;

import com.example.wallet.model.dto.WalletDto;
import com.example.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Validated
public class WalletController {
    private WalletService walletService;

    @Operation(summary = "Create a wallet ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created a client",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = WalletDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request content",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Request to the wrong path or with an error in the URL.",
                    content = @Content),
            @ApiResponse(responseCode = "405", description = "Method is not supported at this URL or the HTTP method is not correct when requested",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Incorrect data entry or the Wallet doesn't exist",
                    content = @Content)
    })

    @PostMapping("/create")
    void newWallet(@RequestBody @Valid WalletDto walletDto) {
        walletService.create(walletDto);
    }

    @Operation(summary = "BalanceOperation ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "BalanceOperation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = WalletDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request content",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Request to the wrong path or with an error in the URL.",
                    content = @Content),
            @ApiResponse(responseCode = "405", description = "Method is not supported at this URL or the HTTP method is not correct when requested",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Incorrect data entry or the Wallet doesn't exist",
                    content = @Content)
    })
    @PutMapping("/wallet")
    void balanceOperation(@RequestBody @Valid WalletDto walletDto) {
        walletService.updateBalance(walletDto);
    }

    @Operation(summary = "Get all wallets ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all wallets",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = WalletDto.class))}),
            @ApiResponse(responseCode = "404", description = "Request to the wrong path or with an error in the URL.",
                    content = @Content),
            @ApiResponse(responseCode = "405", description = "Method is not supported at this URL or the HTTP method is not correct when requested",
                    content = @Content),
    })
    @GetMapping("/wallets")
    public List<WalletDto> getAll() {
        return walletService.findAll();
    }

    @Operation(summary = "Get a wallet by id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get a wallet by id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = WalletDto.class))}),
            @ApiResponse(responseCode = "404", description = "Request to the wrong path or with an error in the URL.",
                    content = @Content),
            @ApiResponse(responseCode = "405", description = "Method is not supported at this URL or the HTTP method is not correct when requested",
                    content = @Content),
    })

    @GetMapping("/{id}")
    public WalletDto getWalletById(@PathVariable Long id) {
        return walletService.findByid(id);
    }

    @Operation(summary = "Delete a wallet by id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete a wallet by id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = WalletDto.class))}),
            @ApiResponse(responseCode = "404", description = "Request to the wrong path or with an error in the URL.",
                    content = @Content),
            @ApiResponse(responseCode = "405", description = "Method is not supported at this URL or the HTTP method is not correct when requested",
                    content = @Content),
    })

    @DeleteMapping("/{id}")
    void deleteWallet(@PathVariable Long id) {
        walletService.delete(id);
    }
}
