package com.learnhub.backend.billing.controller;

import com.learnhub.backend.billing.dto.response.PurchaseResponse;
import com.learnhub.backend.user.dto.response.LibraryResponse;
import com.learnhub.backend.billing.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping("/{userId}")
    public List<PurchaseResponse> history(@PathVariable Long userId){

        return purchaseService.getPurchaseHistory(userId);

    }
    @GetMapping("/library/{userId}")
    public List<LibraryResponse> library(@PathVariable Long userId){

        return purchaseService.getMyLibrary(userId);

    }
}