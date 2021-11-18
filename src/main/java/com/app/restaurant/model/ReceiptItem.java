package com.app.restaurant.model;

import com.app.restaurant.model.enums.ReceiptItemStatus;
import com.app.restaurant.model.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class ReceiptItem {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private int quantity;

    private String additionalNote;

    private ReceiptItemStatus itemStatus;

    @ManyToOne
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;

    @OneToOne
    private Item item;

    @OneToOne
    private User author;

    public ReceiptItem(int quantity, String additionalNote, ReceiptItemStatus itemStatus, Receipt receipt) {
        this.quantity = quantity;
        this.additionalNote = additionalNote;
        this.itemStatus = itemStatus;
        this.receipt = receipt;
    }
}
