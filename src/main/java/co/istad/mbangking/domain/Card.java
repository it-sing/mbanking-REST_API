package co.istad.mbangking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private  long id;

    @Column (unique = true , nullable = false)
    private  String number;

    @Column (nullable = false)
    private  String cvv;

    private  String holder;

    private LocalDateTime issuedAt;
    private LocalDateTime expiredAt;

    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private  CardType cardType;

    @OneToOne(mappedBy = "card")
    private  Account account;
}
