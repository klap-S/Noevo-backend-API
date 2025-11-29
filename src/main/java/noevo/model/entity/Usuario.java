package noevo.model.entity;

//Java imports
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

//Jakarta imports
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;

//Lombok imports
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

//Noevo imports
import noevo.enums.RolUsuario;
import noevo.enums.OpcionesIdiomas;

@Builder
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

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "apellidos", nullable = false)
    private String lastNames;

    @Column(name = "usuario", nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime registrationDate;

    @Column(name = "ultimo_acceso", nullable = true)
    private LocalDateTime lastAccess;

    @Enumerated(EnumType.STRING)
    @Column(name = "idioma", nullable = false)
    private OpcionesIdiomas language;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolUsuario rol;

    // Union con tabla conversacion
    // cascade = CascadeType.ALL: hace que al guardar/eliminar un usuario también se
    // almacenen sus mensajes.
    // orphanRemoval = true: elimina mensajes huérfanos automáticamente si se
    // encuentran.
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conversacion> conversacion = new ArrayList<>();

    // Fin union tablas

}