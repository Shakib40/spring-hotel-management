package com.hotel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table( name = "rooms", uniqueConstraints = { @UniqueConstraint(columnNames = {"hotel_ref_id", "floor", "room_number"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Room {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private String id;

    @NotBlank(message = "Hotel reference ID is required")
    @Size(max = 50, message = "Hotel reference ID cannot exceed 50 characters")
    @Column(name = "hotel_ref_id", nullable = false, length = 50)
    private String hotelRefId;

    @NotNull(message = "Floor is required")
    @Min(value = 1, message = "Floor must be at least 1")
    @Max(value = 100, message = "Floor cannot exceed 100")
    @Column(nullable = false)
    private Integer floor;

    @NotNull(message = "Room number is required")
    @Min(value = 1, message = "Room number must be at least 1")
    @Max(value = 9999, message = "Room number cannot exceed 9999")
    @Column(name = "room_number", nullable = false)
    private Integer roomNumber;

    @NotNull(message = "Availability is required")
    @Column(nullable = false)
    private Boolean availability;


    @NotNull(message = "Room type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "CHAR(36)", length = 20)
    private RoomType roomType;

    public enum RoomType {
        SINGLE, DOUBLE
    }

    @NotNull(message = "Price per day is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per day must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price can have up to 10 digits and 2 decimal places")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal pricePerDay;

    @CreatedDate
    @Column(name = "created_date", updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    // ✅ Runs automatically before INSERT
    @PrePersist
    public void prePersist() {
        if (availability == null) {
            availability = true;
        }
    }
}
