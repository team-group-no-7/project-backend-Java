package com.learnhub.backend.billing.service;

import com.learnhub.backend.billing.dto.response.PurchaseResponse;
import com.learnhub.backend.user.dto.response.LibraryResponse;

import java.util.List;

public interface PurchaseService {

    List<PurchaseResponse> getPurchaseHistory(Long userId);

    List<LibraryResponse> getMyLibrary(Long userId);

}