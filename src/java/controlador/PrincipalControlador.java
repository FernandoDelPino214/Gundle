
package controlador;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.PrimeFaces;

@ManagedBean(name="prplControlador")
@ApplicationScoped
public class PrincipalControlador implements Serializable{

    private final int NUM_FONDOS = 30;
    private Integer indexFondo;
    private String[] rutasFondo;
    
    public PrincipalControlador() {
    }
    
    @PostConstruct
    public void init(){
        generarArrayRutas();
        indexFondo = generarNumeroAleatorio();
    }
    
    public String getRutaFondo(){
        if(indexFondo == null){
            generarNumeroAleatorio();
        }
        return rutasFondo[indexFondo];
    }
    
    public void cambiarFondo(){
        indexFondo = generarNumeroAleatorio(indexFondo);
    }
    
    private void generarArrayRutas(){
        String[] array = new String[NUM_FONDOS];
        for(int i = 0; i < NUM_FONDOS; i++){
            array[i] = "fondo" + (i+1) + ".png";
        }
        rutasFondo = array;
    }
    
    private int generarNumeroAleatorio(){
        return (int) Math.floor(Math.random()*NUM_FONDOS);
    }
    
    // si se da un entero como parámetro se evita que ese número vuelva a salir,
    // para evitar que salga el mismo fondo dos veces seguidas
    private int generarNumeroAleatorio(int excepcion){
        int num = -1;
        do{
            num = generarNumeroAleatorio();
        }
        while(num == excepcion | num == -1);
        return num;
    }
    
    public void mostrarModalInfo(){
        PrimeFaces.current().executeScript("PF('leyendaDialog').show()");
    }
    
    
}
