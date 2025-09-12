package com.system.payment.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pix_payments")
@PrimaryKeyJoinColumn(name = "payment_id")
@Getter
@Setter
public class PixPayment extends Payment {

    @Column(name = "qr_code_url", length = 512)
    private String qrCodeUrl; // URL da imagem do QR Code

    @Column(name = "qr_code_text", length = 512)
    private String qrCodeText; //"PIX Copia e Cola"

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt; // Data e hora de expiração do PIX

}