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
package ec.edu.epn.libres.ejemplofacade.persistencia.dao.generico.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.faces.FacesException;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import ec.edu.epn.libres.ejemplofacade.persistencia.dao.generico.test.util.JsfUtil;
import ec.edu.epn.libres.ejemplofacade.persistencia.dao.generico.test.util.PagingInfo;
import ec.edu.epn.libres.ejemplofacade.persistencia.entidades.Estudiante;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author Xavier Naunay <xavierxc14@gmail.com>
 */
public class EstudianteController {

    public EstudianteController() {
        pagingInfo = new PagingInfo();
        converter = new EstudianteConverter();
    }
    private Estudiante estudiante = null;
    private List<Estudiante> estudianteItems = null;
    private EstudianteFacade jpaController = null;
    private EstudianteConverter converter = null;
    private PagingInfo pagingInfo = null;
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "postgresPU")
    private EntityManagerFactory emf = null;

    public PagingInfo getPagingInfo() {
        if (pagingInfo.getItemCount() == -1) {
            pagingInfo.setItemCount(getJpaController().count());
        }
        return pagingInfo;
    }

    public EstudianteFacade getJpaController() {
        if (jpaController == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            jpaController = (EstudianteFacade) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "estudianteJpa");
        }
        return jpaController;
    }

    public SelectItem[] getEstudianteItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findAll(), false);
    }

    public SelectItem[] getEstudianteItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findAll(), true);
    }

    public Estudiante getEstudiante() {
        if (estudiante == null) {
            estudiante = (Estudiante) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentEstudiante", converter, null);
        }
        if (estudiante == null) {
            estudiante = new Estudiante();
        }
        return estudiante;
    }

    public String listSetup() {
        reset(true);
        return "estudiante_list";
    }

    public String createSetup() {
        reset(false);
        estudiante = new Estudiante();
        return "estudiante_create";
    }

    public String create() {
        try {
            utx.begin();
        } catch (Exception ex) {
        }
        try {
            Exception transactionException = null;
            getJpaController().create(estudiante);
            try {
                utx.commit();
            } catch (javax.transaction.RollbackException ex) {
                transactionException = ex;
            } catch (Exception ex) {
            }
            if (transactionException == null) {
                JsfUtil.addSuccessMessage("Estudiante was successfully created.");
            } else {
                JsfUtil.ensureAddErrorMessage(transactionException, "A persistence error occurred.");
            }
        } catch (Exception e) {
            try {
                utx.rollback();
            } catch (Exception ex) {
            }
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("estudiante_detail");
    }

    public String editSetup() {
        return scalarSetup("estudiante_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        estudiante = (Estudiante) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentEstudiante", converter, null);
        if (estudiante == null) {
            String requestEstudianteString = JsfUtil.getRequestParameter("jsfcrud.currentEstudiante");
            JsfUtil.addErrorMessage("The estudiante with id " + requestEstudianteString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String estudianteString = converter.getAsString(FacesContext.getCurrentInstance(), null, estudiante);
        String currentEstudianteString = JsfUtil.getRequestParameter("jsfcrud.currentEstudiante");
        if (estudianteString == null || estudianteString.length() == 0 || !estudianteString.equals(currentEstudianteString)) {
            String outcome = editSetup();
            if ("estudiante_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit estudiante. Try again.");
            }
            return outcome;
        }
        try {
            utx.begin();
        } catch (Exception ex) {
        }
        try {
            Exception transactionException = null;
            getJpaController().edit(estudiante);
            try {
                utx.commit();
            } catch (javax.transaction.RollbackException ex) {
                transactionException = ex;
            } catch (Exception ex) {
            }
            if (transactionException == null) {
                JsfUtil.addSuccessMessage("Estudiante was successfully updated.");
            } else {
                JsfUtil.ensureAddErrorMessage(transactionException, "A persistence error occurred.");
            }
        } catch (Exception e) {
            try {
                utx.rollback();
            } catch (Exception ex) {
            }
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return detailSetup();
    }

    public String remove() {
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentEstudiante");
        Long id = new Long(idAsString);
        try {
            utx.begin();
        } catch (Exception ex) {
        }
        try {
            Exception transactionException = null;
            getJpaController().remove(getJpaController().find(id));
            try {
                utx.commit();
            } catch (javax.transaction.RollbackException ex) {
                transactionException = ex;
            } catch (Exception ex) {
            }
            if (transactionException == null) {
                JsfUtil.addSuccessMessage("Estudiante was successfully deleted.");
            } else {
                JsfUtil.ensureAddErrorMessage(transactionException, "A persistence error occurred.");
            }
        } catch (Exception e) {
            try {
                utx.rollback();
            } catch (Exception ex) {
            }
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return relatedOrListOutcome();
    }

    private String relatedOrListOutcome() {
        String relatedControllerOutcome = relatedControllerOutcome();
        if (relatedControllerOutcome != null) {
            return relatedControllerOutcome;
        }
        return listSetup();
    }

    public List<Estudiante> getEstudianteItems() {
        if (estudianteItems == null) {
            getPagingInfo();
            estudianteItems = getJpaController().findRange(new int[]{pagingInfo.getFirstItem(), pagingInfo.getFirstItem() + pagingInfo.getBatchSize()});
        }
        return estudianteItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "estudiante_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "estudiante_list";
    }

    private String relatedControllerOutcome() {
        String relatedControllerString = JsfUtil.getRequestParameter("jsfcrud.relatedController");
        String relatedControllerTypeString = JsfUtil.getRequestParameter("jsfcrud.relatedControllerType");
        if (relatedControllerString != null && relatedControllerTypeString != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            Object relatedController = context.getApplication().getELResolver().getValue(context.getELContext(), null, relatedControllerString);
            try {
                Class<?> relatedControllerType = Class.forName(relatedControllerTypeString);
                Method detailSetupMethod = relatedControllerType.getMethod("detailSetup");
                return (String) detailSetupMethod.invoke(relatedController);
            } catch (ClassNotFoundException e) {
                throw new FacesException(e);
            } catch (NoSuchMethodException e) {
                throw new FacesException(e);
            } catch (IllegalAccessException e) {
                throw new FacesException(e);
            } catch (InvocationTargetException e) {
                throw new FacesException(e);
            }
        }
        return null;
    }

    private void reset(boolean resetFirstItem) {
        estudiante = null;
        estudianteItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Estudiante newEstudiante = new Estudiante();
        String newEstudianteString = converter.getAsString(FacesContext.getCurrentInstance(), null, newEstudiante);
        String estudianteString = converter.getAsString(FacesContext.getCurrentInstance(), null, estudiante);
        if (!newEstudianteString.equals(estudianteString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }
    
}
