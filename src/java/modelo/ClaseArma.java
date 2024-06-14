
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "clase_arma")
@NamedQueries({
    @NamedQuery(name = "ClaseArma.findAll", query = "SELECT c FROM ClaseArma c")
    , @NamedQuery(name = "ClaseArma.findById", query = "SELECT c FROM ClaseArma c WHERE c.id = :id")
    , @NamedQuery(name = "ClaseArma.findByNombre", query = "SELECT c FROM ClaseArma c WHERE c.nombre = :nombre")})
public class ClaseArma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clase", fetch = FetchType.EAGER)
    private List<Arma> armaList;

    public ClaseArma() {
    }

    public ClaseArma(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Arma> getArmaList() {
        return armaList;
    }

    public void setArmaList(List<Arma> armaList) {
        this.armaList = armaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClaseArma)) {
            return false;
        }
        ClaseArma other = (ClaseArma) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ClaseArma[ id=" + id + " ]";
    }
    
}
