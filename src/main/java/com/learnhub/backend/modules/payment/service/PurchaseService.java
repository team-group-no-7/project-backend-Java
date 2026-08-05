package com.learnhub.backend.modules.payment.service;

import com.learnhub.backend.modules.payment.dto.response.PurchaseResponse;
import com.learnhub.backend.modules.user.dto.response.LibraryResponse;

import java.util.List;

public interface PurchaseService {

    List<PurchaseResponse> getPurchaseHistory(Long userId);

    List<LibraryResponse> getMyLibrary(Long userId);

}