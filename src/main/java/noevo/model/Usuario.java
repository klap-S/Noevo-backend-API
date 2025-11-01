package noevo.model;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String nombre = "invitado";

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @Column(name = "ultimo_acceso", nullable = true)
    private LocalDateTime ultimoAcceso;

    // Union con tabla mensaje y conversacion
    // cascade = CascadeType.ALL: hace que al guardar/eliminar un usuario también se
    // almacenen sus mensajes.
    // orphanRemoval = true: elimina mensajes huérfanos automáticamente si se
    // encuentran.
    @OneToMany(mappedBy = "usuario")
    private List<Mensaje> mensajes = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conversacion> conversaciones = new ArrayList<>();

    // Fin union tablas

}