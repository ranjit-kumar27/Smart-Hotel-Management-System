package com.ranjit.project.airBnbApp.service;

import com.ranjit.project.airBnbApp.dto.HotelDto;
import com.ranjit.project.airBnbApp.dto.HotelInfoDto;
import com.ranjit.project.airBnbApp.dto.RoomDto;
import com.ranjit.project.airBnbApp.entity.Hotel;
import com.ranjit.project.airBnbApp.entity.Room;
import com.ranjit.project.airBnbApp.entity.User;
import com.ranjit.project.airBnbApp.exception.ResourceNotFoundException;
import com.ranjit.project.airBnbApp.exception.UnAuthorisedException;
import com.ranjit.project.airBnbApp.repository.HotelRepository;
import com.ranjit.project.airBnbApp.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ranjit.project.airBnbApp.util.AppUtils.getCurrentUser;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl  implements  HotelService{

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;
    private final RoomRepository roomRepository;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating a new hotel with name:{}",hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);//convert HotelDto to Hotel
        hotel.setActive(false);

        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        hotel.setOwner(user);

        hotelRepository.save(hotel);
        log.info("created a new hotel  with id:{}",hotel.getId());

        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting hotel with id:{}",id);
        Hotel hotel=hotelRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found id "+id));
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotel.getOwner())) {
            throw new UnAuthorisedException("This user does not own this hotel with id: "+hotel.getId());
        }

        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        log.info("updating hotel with id:{}",id);
        Hotel hotel=hotelRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Hotel not found with id "+id));

        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(hotel.getOwner())) {
            throw new UnAuthorisedException("This user does not own this hotel with id: "+hotel.getId());
        }

        modelMapper.map(hotelDto,hotel);//source -> destination
        hotel.setId(id);
        hotel=hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        Hotel hotel=hotelRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Hotel not found with id "+id));

        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(hotel.getOwner())) {
            throw new UnAuthorisedException("This user does not own this hotel with id: "+hotel.getId());
        }

        for(Room room:hotel.getRooms()){
            inventoryService.deleteAllInventories(room);
            roomRepository.deleteById(room.getId());
        }
        hotelRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void activateHotel(Long hotelId) {
        log.info("activating hotel with id:{}",hotelId);
        Hotel hotel=hotelRepository.findById(hotelId)
                .orElseThrow(()->new RuntimeException("Hotel not found with id "+hotelId));

        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(hotel.getOwner())) {
            throw new UnAuthorisedException("This user does not own this hotel with id: "+hotel.getId());
        }

        hotel.setActive(true);

        //assuming only do it once
        for(Room room:hotel.getRooms()){
            inventoryService.initializeRoomForYear(room);
        }
    }

    //public method
    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {
        Hotel hotel=hotelRepository.findById(hotelId)
                .orElseThrow(()->new RuntimeException("Hotel not found with id "+hotelId));
        List<RoomDto> rooms=hotel.getRooms()
                .stream()
                .map((element)->modelMapper.map(element, RoomDto.class))
                .toList();
        return new HotelInfoDto(modelMapper.map(hotel,HotelDto.class),rooms);
    }

    @Override
    public List<HotelDto> getAllHotels() {
        User user=getCurrentUser();
        log.info("Getting all hotels for the admin user with ID: {}",user.getId());
        List<Hotel> hotels=hotelRepository.findByOwner(user);

        return hotels.stream()
                .map((element)->modelMapper.map(element,HotelDto.class))
                .collect(Collectors.toList());
    }
}
