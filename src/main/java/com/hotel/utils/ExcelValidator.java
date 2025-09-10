package com.hotel.utils;

import com.hotel.entity.Room;
import org.apache.poi.ss.usermodel.*;

import java.math.BigDecimal;

public class ExcelValidator {

    public static boolean isRowEmpty(Row row) {
        for (int i = 0; i < row.getLastCellNum(); i++) {
            if (row.getCell(i) != null && row.getCell(i).getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    public static String getStringCellValue(Row row, int cellIndex, String fieldName) {
        Cell cell = row.getCell(cellIndex);
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            throw new IllegalArgumentException(fieldName + " is missing");
        }
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((int) cell.getNumericCellValue());
        }
        throw new IllegalArgumentException(fieldName + " has invalid type");
    }

    public static double getNumericCellValue(Row row, int cellIndex, String fieldName) {
        Cell cell = row.getCell(cellIndex);
        if (cell == null) {
            throw new IllegalArgumentException(fieldName + " is missing");
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        }
        throw new IllegalArgumentException(fieldName + " must be a number");
    }

    public static boolean getBooleanCellValue(Row row, int cellIndex, String fieldName) {
        Cell cell = row.getCell(cellIndex);
        if (cell == null) {
            throw new IllegalArgumentException(fieldName + " is missing");
        }
        if (cell.getCellType() == CellType.BOOLEAN) {
            return cell.getBooleanCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            String value = cell.getStringCellValue().trim().toLowerCase();
            if (value.equals("true") || value.equals("yes")) return true;
            if (value.equals("false") || value.equals("no")) return false;
        }
        throw new IllegalArgumentException(fieldName + " must be true/false");
    }

    public static Room.RoomType getRoomType(Row row, int cellIndex) {
        String roomTypeValue = getStringCellValue(row, cellIndex, "Room Type").toUpperCase().trim();
        try {
            return Room.RoomType.valueOf(roomTypeValue);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid Room Type: " + roomTypeValue + " (allowed: SINGLE, DOUBLE)");
        }
    }

    public static BigDecimal getPrice(Row row, int cellIndex) {
        double priceValue = getNumericCellValue(row, cellIndex, "Price Per Day");
        if (priceValue <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        return BigDecimal.valueOf(priceValue);
    }
}
