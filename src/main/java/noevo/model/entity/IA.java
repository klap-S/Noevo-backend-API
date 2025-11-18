package noevo.model.entity;

//Java imports
import java.util.ArrayList;
import java.util.List;

//Jakarta imports
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

//Lombok imports
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "IA")
public class IA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String name;

    @Column(name = "modelo", nullable = false)
    private String model;

    @Column(name = "idioma", nullable = false)
    private String language;

    // Union con tabla conversacion
    @OneToMany(mappedBy = "ia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conversacion> conversacion = new ArrayList<>();

    // Fin union tablas

}