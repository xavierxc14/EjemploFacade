package ec.edu.epn.libres.ejemplofacade.controladores.vista;

import ec.edu.epn.libres.ejemplofacade.controladores.sesion.ProfesorBB;
import ec.edu.epn.libres.ejemplofacade.persistencia.clases.util.JsfUtil;
import ec.edu.epn.libres.ejemplofacade.persistencia.clases.util.JsfUtil.PersistAction;
import ec.edu.epn.libres.ejemplofacade.persistencia.dao.derby.ProfesorDerbyJPA;
import ec.edu.epn.libres.ejemplofacade.persistencia.dao.generico.GenericoJPA;
import ec.edu.epn.libres.ejemplofacade.persistencia.dao.postgres.ProfesorPostgresJPA;
import ec.edu.epn.libres.ejemplofacade.persistencia.entidades.Profesor;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "profesorBean")
@SessionScoped
public class ProfesorControlador implements Serializable {

    @EJB
    private ProfesorPostgresJPA profesorPostgresJPA;
    @EJB
    private ProfesorDerbyJPA profesorDerbyJPA;
    @ManagedProperty("#{estudianteBB}")
    private ProfesorBB profesorBB;

    public ProfesorControlador() {
    }

    private GenericoJPA getFacade() {
        if (InicioControlador.seleccion == 2) {
            return profesorDerbyJPA;
        } else {
            return profesorPostgresJPA;
        }
    }

    public Profesor prepareCreate() {
        profesorBB.setSelected(new Profesor());
        return profesorBB.getSelected();
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProfesorCreated"));
        if (!JsfUtil.isValidationFailed()) {
            profesorBB.setItems(null);    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProfesorUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProfesorDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            profesorBB.setSelected(null); // Remove selection
            profesorBB.setItems(null);    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Profesor> getItems() {
            profesorBB.setItems(getFacade().findAll());
        return profesorBB.getItems();
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (profesorBB.getSelected() != null) {
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(profesorBB.getSelected());
                } else {
                    getFacade().remove(profesorBB.getSelected());
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<Profesor> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Profesor> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Profesor.class)
    public static class ProfesorControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProfesorControlador controller = (ProfesorControlador) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "profesorController");
            return controller.getFacade().find(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Profesor) {
                Profesor o = (Profesor) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Profesor.class.getName()});
                return null;
            }
        }

    }

}
