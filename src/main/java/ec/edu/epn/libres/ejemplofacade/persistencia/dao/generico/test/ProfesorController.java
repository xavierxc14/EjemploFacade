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
import ec.edu.epn.libres.ejemplofacade.persistencia.entidades.Profesor;
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
public class ProfesorController {

    public ProfesorController() {
        pagingInfo = new PagingInfo();
        converter = new ProfesorConverter();
    }
    private Profesor profesor = null;
    private List<Profesor> profesorItems = null;
    private ProfesorFacade jpaController = null;
    private ProfesorConverter converter = null;
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

    public ProfesorFacade getJpaController() {
        if (jpaController == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            jpaController = (ProfesorFacade) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "profesorJpa");
        }
        return jpaController;
    }

    public SelectItem[] getProfesorItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findAll(), false);
    }

    public SelectItem[] getProfesorItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findAll(), true);
    }

    public Profesor getProfesor() {
        if (profesor == null) {
            profesor = (Profesor) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentProfesor", converter, null);
        }
        if (profesor == null) {
            profesor = new Profesor();
        }
        return profesor;
    }

    public String listSetup() {
        reset(true);
        return "profesor_list";
    }

    public String createSetup() {
        reset(false);
        profesor = new Profesor();
        return "profesor_create";
    }

    public String create() {
        try {
            utx.begin();
        } catch (Exception ex) {
        }
        try {
            Exception transactionException = null;
            getJpaController().create(profesor);
            try {
                utx.commit();
            } catch (javax.transaction.RollbackException ex) {
                transactionException = ex;
            } catch (Exception ex) {
            }
            if (transactionException == null) {
                JsfUtil.addSuccessMessage("Profesor was successfully created.");
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
        return scalarSetup("profesor_detail");
    }

    public String editSetup() {
        return scalarSetup("profesor_edit");
    }

    private String scalarSetup(String destination) {
        reset(false);
        profesor = (Profesor) JsfUtil.getObjectFromRequestParameter("jsfcrud.currentProfesor", converter, null);
        if (profesor == null) {
            String requestProfesorString = JsfUtil.getRequestParameter("jsfcrud.currentProfesor");
            JsfUtil.addErrorMessage("The profesor with id " + requestProfesorString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
        String profesorString = converter.getAsString(FacesContext.getCurrentInstance(), null, profesor);
        String currentProfesorString = JsfUtil.getRequestParameter("jsfcrud.currentProfesor");
        if (profesorString == null || profesorString.length() == 0 || !profesorString.equals(currentProfesorString)) {
            String outcome = editSetup();
            if ("profesor_edit".equals(outcome)) {
                JsfUtil.addErrorMessage("Could not edit profesor. Try again.");
            }
            return outcome;
        }
        try {
            utx.begin();
        } catch (Exception ex) {
        }
        try {
            Exception transactionException = null;
            getJpaController().edit(profesor);
            try {
                utx.commit();
            } catch (javax.transaction.RollbackException ex) {
                transactionException = ex;
            } catch (Exception ex) {
            }
            if (transactionException == null) {
                JsfUtil.addSuccessMessage("Profesor was successfully updated.");
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
        String idAsString = JsfUtil.getRequestParameter("jsfcrud.currentProfesor");
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
                JsfUtil.addSuccessMessage("Profesor was successfully deleted.");
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

    public List<Profesor> getProfesorItems() {
        if (profesorItems == null) {
            getPagingInfo();
            profesorItems = getJpaController().findRange(new int[]{pagingInfo.getFirstItem(), pagingInfo.getFirstItem() + pagingInfo.getBatchSize()});
        }
        return profesorItems;
    }

    public String next() {
        reset(false);
        getPagingInfo().nextPage();
        return "profesor_list";
    }

    public String prev() {
        reset(false);
        getPagingInfo().previousPage();
        return "profesor_list";
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
        profesor = null;
        profesorItems = null;
        pagingInfo.setItemCount(-1);
        if (resetFirstItem) {
            pagingInfo.setFirstItem(0);
        }
    }

    public void validateCreate(FacesContext facesContext, UIComponent component, Object value) {
        Profesor newProfesor = new Profesor();
        String newProfesorString = converter.getAsString(FacesContext.getCurrentInstance(), null, newProfesor);
        String profesorString = converter.getAsString(FacesContext.getCurrentInstance(), null, profesor);
        if (!newProfesorString.equals(profesorString)) {
            createSetup();
        }
    }

    public Converter getConverter() {
        return converter;
    }
    
}
