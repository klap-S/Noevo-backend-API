package noevo.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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

    @Column(nullable = false)
    private String nombre = "Carla";

    @Column(nullable = false)
    private String modelo = "AsistenteIA-V1";

    @Column(nullable = false)
    private String idioma = "Español";

    // Union con tabla mensaje y conversacion
    @OneToMany(mappedBy = "ia")
    private List<Mensaje> mensajes = new ArrayList<>();

    @OneToMany(mappedBy = "ia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conversacion> conversaciones = new ArrayList<>();
    // Fin union tablas

}