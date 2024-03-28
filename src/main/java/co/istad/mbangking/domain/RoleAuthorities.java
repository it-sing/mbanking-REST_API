package co.istad.mbangking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "role_authorities")
public class RoleAuthorities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer authority_id;
    @ManyToOne
    private Role role;

}
