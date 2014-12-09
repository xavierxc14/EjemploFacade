package ec.edu.epn.libres.ejemplofacade.controladores.sesion;

import ec.edu.epn.libres.ejemplofacade.persistencia.entidades.Profesor;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "profesorBB")
@SessionScoped
public class ProfesorBB implements Serializable {

    private List<Profesor> items = null;
    private Profesor selected;

    public ProfesorBB() {
    }

    public Profesor getSelected() {
        return selected;
    }

    public void setSelected(Profesor selected) {
        this.selected = selected;
    }

    public List<Profesor> getItems() {
        return items;
    }

    public void setItems(List<Profesor> items) {
        this.items = items;
    }

}
