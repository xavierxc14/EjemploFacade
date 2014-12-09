<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Listing Profesor Items</title>
            <link rel="stylesheet" type="text/css" href="/Conexion/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Listing Profesor Items</h1>
            <h:form styleClass="jsfcrud_list_form">
                <h:outputText escape="false" value="(No Profesor Items Found)<br />" rendered="#{profesor.pagingInfo.itemCount == 0}" />
                <h:panelGroup rendered="#{profesor.pagingInfo.itemCount > 0}">
                    <h:outputText value="Item #{profesor.pagingInfo.firstItem + 1}..#{profesor.pagingInfo.lastItem} of #{profesor.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{profesor.prev}" value="Previous #{profesor.pagingInfo.batchSize}" rendered="#{profesor.pagingInfo.firstItem >= profesor.pagingInfo.batchSize}"/>&nbsp;
                    <h:commandLink action="#{profesor.next}" value="Next #{profesor.pagingInfo.batchSize}" rendered="#{profesor.pagingInfo.lastItem + profesor.pagingInfo.batchSize <= profesor.pagingInfo.itemCount}"/>&nbsp;
                    <h:commandLink action="#{profesor.next}" value="Remaining #{profesor.pagingInfo.itemCount - profesor.pagingInfo.lastItem}"
                                   rendered="#{profesor.pagingInfo.lastItem < profesor.pagingInfo.itemCount && profesor.pagingInfo.lastItem + profesor.pagingInfo.batchSize > profesor.pagingInfo.itemCount}"/>
                    <h:dataTable value="#{profesor.profesorItems}" var="item" border="0" cellpadding="2" cellspacing="0" rowClasses="jsfcrud_odd_row,jsfcrud_even_row" rules="all" style="border:solid 1px">
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Id"/>
                            </f:facet>
                            <h:outputText value="#{item.id}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Nombres"/>
                            </f:facet>
                            <h:outputText value="#{item.nombres}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Apellidos"/>
                            </f:facet>
                            <h:outputText value="#{item.apellidos}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Cedula"/>
                            </f:facet>
                            <h:outputText value="#{item.cedula}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Direccion"/>
                            </f:facet>
                            <h:outputText value="#{item.direccion}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText escape="false" value="&nbsp;"/>
                            </f:facet>
                            <h:commandLink value="Show" action="#{profesor.detailSetup}">
                                <f:param name="jsfcrud.currentProfesor" value="#{jsfcrud_class['ec.edu.epn.libres.conexion.persistencia.dao.generico.test.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][profesor.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Edit" action="#{profesor.editSetup}">
                                <f:param name="jsfcrud.currentProfesor" value="#{jsfcrud_class['ec.edu.epn.libres.conexion.persistencia.dao.generico.test.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][profesor.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                            <h:outputText value=" "/>
                            <h:commandLink value="Destroy" action="#{profesor.remove}">
                                <f:param name="jsfcrud.currentProfesor" value="#{jsfcrud_class['ec.edu.epn.libres.conexion.persistencia.dao.generico.test.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][item][profesor.converter].jsfcrud_invoke}"/>
                            </h:commandLink>
                        </h:column>

                    </h:dataTable>
                </h:panelGroup>
                <br />
                <h:commandLink action="#{profesor.createSetup}" value="New Profesor"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />


            </h:form>
        </body>
    </html>
</f:view>
