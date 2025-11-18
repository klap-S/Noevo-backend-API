package noevo.model.entity;

//Java imports
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

//Jakarta imports
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

//Lombok imports
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

//Noevo imports
import noevo.enums.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Mensaje")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "orden", nullable = false)
    private Integer order;

    @Enumerated(EnumType.STRING)
    @Column(name = "emisor", nullable = false)
    private OpcionesRemitente speaker;

    @Enumerated(EnumType.STRING)
    @Column(name = "remitente", nullable = false)
    private OpcionesRemitente sender;

    // Texto en base de datos con Lob
    @Lob
    @Column(name = "contenido_texto", nullable = true)
    private String contentText;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private OpcionesTipoMensajes type;

    @Column(name = "audio_url", nullable = true)
    private String audioUrl;

    @Column(name = "fecha_envio")
    private LocalDateTime shippingDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    // Union con tablas conversacion
    @ManyToOne
    @JoinColumn(name = "conversacion_id")
    private Conversacion conversacion;

    // Fin union tablas

}
