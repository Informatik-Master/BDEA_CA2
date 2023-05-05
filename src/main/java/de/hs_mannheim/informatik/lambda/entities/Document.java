package de.hs_mannheim.informatik.lambda.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "document")
public class Document {

    public enum DocumentType {
        SOURCE,
        TARGET,
        OVERALL_TARGET
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "filename")
    private String filename;

    @Column(name = "warehousefilename")
    private String warehouseFilename;

    @Column(name = "contenttype")
    private String contentType;

    @Column(name = "documenttype")
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @OneToOne
    private Document source;
}
