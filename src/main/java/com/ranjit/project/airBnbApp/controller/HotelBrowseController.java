package com.ranjit.project.airBnbApp.controller;

import com.ranjit.project.airBnbApp.dto.HotelDto;
import com.ranjit.project.airBnbApp.dto.HotelInfoDto;
import com.ranjit.project.airBnbApp.dto.HotelPriceDto;
import com.ranjit.project.airBnbApp.dto.HotelSearchRequest;
import com.ranjit.project.airBnbApp.service.HotelService;
import com.ranjit.project.airBnbApp.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/hotel")
@RequiredArgsConstructor
public class HotelBrowseController {

    private final InventoryService inventoryService;
    private final HotelService hotelService;

    @GetMapping("/search")
    public ResponseEntity<Page<HotelPriceDto>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest){

            var page=inventoryService.searchHotels(hotelSearchRequest);
            return ResponseEntity.ok(page);
    }

    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable("hotelId") Long hotelId){
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }
}
