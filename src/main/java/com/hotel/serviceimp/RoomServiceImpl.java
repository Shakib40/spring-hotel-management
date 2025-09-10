package com.hotel.serviceimp;
import com.hotel.dto.RequestRoom;
import com.hotel.dto.RoomBulkUploadResponse;
import com.hotel.entity.Room;
import com.hotel.repository.RoomRepository;
import com.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.hotel.utils.ExcelValidator;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public Room createRoom(RequestRoom requestRoom) {
        // Prevent duplicate room in same hotel & floor

        if (roomRepository.existsRoom(requestRoom.getHotelRefId(), requestRoom.getFloor(), requestRoom.getRoomNumber())) {
            throw new IllegalArgumentException("Room already exists for this hotel & floor");
        }

        // ✅ Map DTO → Entity
        Room room = new Room();
        room.setHotelRefId(requestRoom.getHotelRefId());
        room.setFloor(requestRoom.getFloor());
        room.setRoomNumber(requestRoom.getRoomNumber());
        room.setAvailability(requestRoom.getAvailability() != null ? requestRoom.getAvailability() : true);
        room.setRoomType(requestRoom.getRoomType());
        room.setPricePerDay(requestRoom.getPricePerDay());

        // ✅ Save entity
        return roomRepository.save(room);
    }


    public RoomBulkUploadResponse uploadRoomsFromExcel(MultipartFile file) {
        int successCount = 0;
        List<String> errors = new ArrayList<>();
        int totalRecords = 0;

        if (!file.getOriginalFilename().endsWith(".xlsx")) {
            errors.add("Invalid file type. Only .xlsx files are supported.");
            return new RoomBulkUploadResponse(0, 0, errors.size(), errors);
        }

        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null || ExcelValidator.isRowEmpty(row)) continue;

                totalRecords++;
                try {
                    Room room = new Room();

                    //Note. Here we will take HotelRefId from token. Like from userRef we will fetch hotelRef, only one Hotel should have to user

                    room.setHotelRefId(ExcelValidator.getStringCellValue(row, 0, "HotelRefId"));
                    room.setFloor((int) ExcelValidator.getNumericCellValue(row, 1, "Floor"));
                    room.setRoomNumber((int) ExcelValidator.getNumericCellValue(row, 2, "Room Number"));
                    room.setAvailability(ExcelValidator.getBooleanCellValue(row, 3, "Availability"));
                    room.setRoomType(ExcelValidator.getRoomType(row, 4));
                    room.setPricePerDay(ExcelValidator.getPrice(row, 5));

                    // Check duplicates
                    boolean exists = roomRepository.existsRoom(
                            room.getHotelRefId(), room.getFloor(), room.getRoomNumber());
                    if (exists) {
                        throw new IllegalArgumentException("Duplicate room detected: Floor " +
                                room.getFloor() + ", Room " + room.getRoomNumber());
                    }
                    System.out.println("roomroomroom" + room);

                    //Note. Here we will take HotelRefId from token. Like from userRef we will fetch hotelRef, only one Hotel should have to user
//                    roomRepository.save(room);
                    successCount++;

                } catch (Exception e) {
                    errors.add("Row " + (i + 1) + " failed: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            errors.add("File processing error: " + e.getMessage());
        }

        return new RoomBulkUploadResponse(
                totalRecords,
                successCount,
                errors.size(),
                errors
        );
    }

    @Override
    public byte[] generateRoomExcelTemplate() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("RoomsTemplate");

            // Headers
            Row headerRow = sheet.createRow(0);
            String[] headers = {"hotelRefId", "floor", "roomNumber", "availability", "roomType", "pricePerDay"};

            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            // Sample row
            Row sampleRow = sheet.createRow(1);
            sampleRow.createCell(0).setCellValue("0627b7e4-cc7f-4622-9f0e-af9e69c6a504");
            sampleRow.createCell(1).setCellValue(4);
            sampleRow.createCell(2).setCellValue(402);
            sampleRow.createCell(3).setCellValue(true);
            sampleRow.createCell(4).setCellValue("SINGLE");
            sampleRow.createCell(5).setCellValue(1500.00);

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Excel template", e);
        }
    }

    @Override
    public List<Room> getAvailableRoomsByHotel(String hotelRefId) {
        return roomRepository.findByHotelRefIdAndAvailabilityTrue(hotelRefId);
    }

    @Override
    public List<Room> getBookedRoomsByHotel(String hotelRefId) {
        return roomRepository.findByHotelRefIdAndAvailabilityFalse(hotelRefId);
    }

}
