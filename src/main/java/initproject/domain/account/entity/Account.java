package initproject.domain.account.entity;

import initproject.global.auditing.BaseTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Account extends BaseTime {

    @Id @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<Role> roleList;
}
