package io.github.rusyasoft.example.bank.ipoteka.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "User")
@Entity
public class UserEntity {

    @Id
    private Long id;

    @Column(nullable = false, length=50)
    private String name;


    /** member 파트와 협업 구조에 따라 필요 없을 수 있다 **/
    @Column(nullable = false, length=200)
    private String password;

    @Column(length=500)
    private String email;


}
