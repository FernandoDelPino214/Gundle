
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "arma")
@NamedQueries({
    @NamedQuery(name = "Arma.findAll", query = "SELECT a FROM Arma a")
    , @NamedQuery(name = "Arma.findById", query = "SELECT a FROM Arma a WHERE a.id = :id")
    , @NamedQuery(name = "Arma.findByNombre", query = "SELECT a FROM Arma a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Arma.findByCalidad", query = "SELECT a FROM Arma a WHERE a.calidad = :calidad")
    , @NamedQuery(name = "Arma.findByCargador", query = "SELECT a FROM Arma a WHERE a.cargador = :cargador")
    , @NamedQuery(name = "Arma.findByMunicion", query = "SELECT a FROM Arma a WHERE a.municion = :municion")
    , @NamedQuery(name = "Arma.findByProyectiles", query = "SELECT a FROM Arma a WHERE a.proyectiles = :proyectiles")
    , @NamedQuery(name = "Arma.findByImgRuta", query = "SELECT a FROM Arma a WHERE a.imgRuta = :imgRuta")})
public class Arma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "calidad")
    private Character calidad;
    @Column(name = "cargador")
    private Integer cargador;
    @Column(name = "municion")
    private Integer municion;
    @Column(name = "proyectiles")
    private Integer proyectiles;
    @Column(name = "img_ruta")
    private String imgRuta;
    @JoinTable(name = "arma_color", joinColumns = {
        @JoinColumn(name = "id_arma", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "id_color", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Color> colorList;
    @JoinColumn(name = "clase", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private ClaseArma clase;
    @JoinColumn(name = "tipo", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TipoArma tipo;

    public Arma() {
    }

    public Arma(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Character getCalidad() {
        return calidad;
    }

    public void setCalidad(Character calidad) {
        this.calidad = calidad;
    }

    public Integer getCargador() {
        return cargador;
    }

    public void setCargador(Integer cargador) {
        this.cargador = cargador;
    }

    public Integer getMunicion() {
        return municion;
    }

    public void setMunicion(Integer municion) {
        this.municion = municion;
    }

    public Integer getProyectiles() {
        return proyectiles;
    }

    public void setProyectiles(Integer proyectiles) {
        this.proyectiles = proyectiles;
    }

    public String getImgRuta() {
        return imgRuta;
    }

    public void setImgRuta(String imgRuta) {
        this.imgRuta = imgRuta;
    }

    public List<Color> getColorList() {
        return colorList;
    }

    public void setColorList(List<Color> colorList) {
        this.colorList = colorList;
    }

    public ClaseArma getClase() {
        return clase;
    }

    public void setClase(ClaseArma clase) {
        this.clase = clase;
    }

    public TipoArma getTipo() {
        return tipo;
    }

    public void setTipo(TipoArma tipo) {
        this.tipo = tipo;
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
        if (!(object instanceof Arma)) {
            return false;
        }
        Arma other = (Arma) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Arma[ id=" + id + " ]";
    }
    
}
