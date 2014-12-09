/*
 * Copyright (C) 2014 Xavier Naunay <xavierxc14@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ec.edu.epn.libres.ejemplofacade.controladores.vista;

import ec.edu.epn.libres.ejemplofacade.controladores.sesion.EstudianteBB;
import ec.edu.epn.libres.ejemplofacade.persistencia.clases.util.JsfUtil;
import ec.edu.epn.libres.ejemplofacade.persistencia.clases.util.JsfUtil.PersistAction;
import ec.edu.epn.libres.ejemplofacade.persistencia.dao.derby.EstudianteDerbyJPA;
import ec.edu.epn.libres.ejemplofacade.persistencia.dao.generico.GenericoJPA;
import ec.edu.epn.libres.ejemplofacade.persistencia.dao.postgres.EstudiantePostgresJPA;
import ec.edu.epn.libres.ejemplofacade.persistencia.entidades.Estudiante;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Xavier Naunay <xavierxc14@gmail.com>
 */
@ManagedBean(name = "estudianteBean")
@ViewScoped
public class EstudianteControlador implements Serializable {

    @EJB
    private EstudiantePostgresJPA estudiantePostgresJPA;
    @EJB
    private EstudianteDerbyJPA estudianteDerbyJPA;

    @ManagedProperty("#{estudianteBB}")
    private EstudianteBB estudianteBB;

    public EstudianteControlador() {
        getFacade();
    }

    private GenericoJPA getFacade() {
        if (InicioControlador.seleccion == 2) {
            return estudianteDerbyJPA;
        } else {
            return estudiantePostgresJPA;
        }
    }

    @PostConstruct
    public void init() {
        getItems();
    }

    public Estudiante prepareCreate() {
        estudianteBB.setSelected(new Estudiante());
        return estudianteBB.getSelected();
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EstudianteCreated"));
        if (!JsfUtil.isValidationFailed()) {
            estudianteBB.setItems(null);    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EstudianteUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EstudianteDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            estudianteBB.setSelected(null); // Remove selection
            estudianteBB.setItems(null);    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Estudiante> getItems() {
        estudianteBB.setItems(getFacade().findAll());
        return estudianteBB.getItems();
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (estudianteBB.getSelected() != null) {
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(estudianteBB.getSelected());
                } else {
                    getFacade().remove(estudianteBB.getSelected());
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

    public List<Estudiante> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Estudiante> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Estudiante.class)
    public static class EstudianteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EstudianteControlador controller = (EstudianteControlador) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "estudianteController");
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
            if (object instanceof Estudiante) {
                Estudiante o = (Estudiante) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Estudiante.class.getName()});
                return null;
            }
        }

    }

    public EstudianteBB getEstudianteBB() {
        return estudianteBB;
    }

    public void setEstudianteBB(EstudianteBB estudianteBB) {
        this.estudianteBB = estudianteBB;
    }

}
