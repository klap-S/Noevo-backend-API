package noevo.model.entity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import noevo.enums.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Mensaje")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer orden;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OpcionesRemitente emisor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OpcionesRemitente remitente;

    // Texto en base de datos con Lob
    @Lob
    @Column(nullable = true)
    private String contenidoTexto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OpcionesTipoMensajes tipo;

    @Column(nullable = true)
    private String audioUrl;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);;

    // Union con tablas ususario, ia y conversacion
    @ManyToOne
    @JoinColumn(name = "conversacion_id")
    private Conversacion conversacion;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = true)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ia_id", nullable = true)
    private IA ia;
    // Fin union tablas

}
