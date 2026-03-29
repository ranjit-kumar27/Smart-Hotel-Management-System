package com.ranjit.project.airBnbApp.service;

import com.ranjit.project.airBnbApp.dto.RoomDto;
import com.ranjit.project.airBnbApp.entity.Hotel;
import com.ranjit.project.airBnbApp.entity.Room;
import com.ranjit.project.airBnbApp.entity.User;
import com.ranjit.project.airBnbApp.exception.ResourceNotFoundException;
import com.ranjit.project.airBnbApp.exception.UnAuthorisedException;
import com.ranjit.project.airBnbApp.repository.HotelRepository;
import com.ranjit.project.airBnbApp.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ranjit.project.airBnbApp.util.AppUtils.getCurrentUser;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final ModelMapper modelMapper;

    @Override
    public RoomDto createRoom(Long hotelId,RoomDto roomDto) {
        log.info("creating room new room in hotel with Id :{}", hotelId);
        Hotel hotel =  hotelRepository
                .findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with id :"+ hotelId));

        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(hotel.getOwner())) {
            throw new UnAuthorisedException("This user does not own this hotel with id: "+hotelId);
        }

        Room room = modelMapper.map(roomDto, Room.class);
        room.setHotel(hotel);
        room=roomRepository.save(room);

        if(hotel.getActive()){
            inventoryService.initializeRoomForYear(room);
        }

        return modelMapper.map(room,RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
        log.info("getting all rooms in hotel with Id :{}", hotelId);
        //check hotel exist or not
        Hotel hotel =  hotelRepository
                .findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with id :"+ hotelId));

        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(hotel.getOwner())) {
            throw new UnAuthorisedException("This user does not own this hotel with id: "+hotelId);
        }

        return hotel.getRooms()
                .stream()
                .map((element)->modelMapper.map(element,RoomDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        log.info("Getting the room with ID: {}", roomId);
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: "+roomId));
        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public void deleteRoomById(Long roomId) {
        log.info("Deleting the room with ID: {}", roomId);
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: "+roomId));

        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(room.getHotel().getOwner())) {
            throw new UnAuthorisedException("This user does not own this room with id: "+roomId);
        }

        inventoryService.deleteAllInventories(room);
        roomRepository.deleteById(roomId);
    }

    @Override
    public RoomDto updateRoomById(Long hotelId, Long roomId, RoomDto roomDto) {
        log.info("Updating the room with ID: {}", roomId);
        Hotel hotel=hotelRepository
                .findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with id: "+hotelId));

        User user=getCurrentUser();
        if(!user.equals(hotel.getOwner())) {
            throw new UnAuthorisedException("This user does not own this hotel with id: "+hotelId);
        }

        Room room =roomRepository.findById(roomId).orElseThrow(
                ()->new ResourceNotFoundException("Room not found with ID: "+roomId));

        modelMapper.map(room,roomDto);
        room.setId(roomId);
        room=roomRepository.save(room);

        return null;
    }
}
