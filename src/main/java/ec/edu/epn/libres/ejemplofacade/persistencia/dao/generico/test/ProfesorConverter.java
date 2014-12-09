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

import ec.edu.epn.libres.ejemplofacade.persistencia.entidades.Profesor;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author Xavier Naunay <xavierxc14@gmail.com>
 */
public class ProfesorConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Long id = new Long(string);
        ProfesorController controller = (ProfesorController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "profesor");
        return controller.getJpaController().find(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Profesor) {
            Profesor o = (Profesor) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: ec.edu.epn.libres.conexion.persistencia.entidades.Profesor");
        }
    }
    
}
