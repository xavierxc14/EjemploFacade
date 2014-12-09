<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <title>Editing Profesor</title>
            <link rel="stylesheet" type="text/css" href="/Conexion/faces/jsfcrud.css" />
        </head>
        <body>
            <h:panelGroup id="messagePanel" layout="block">
                <h:messages errorStyle="color: red" infoStyle="color: green" layout="table"/>
            </h:panelGroup>
            <h1>Editing Profesor</h1>
            <h:form>
                <h:panelGrid columns="2">
                    <h:outputText value="Id:"/>
                    <h:outputText value="#{profesor.profesor.id}" title="Id" />
                    <h:outputText value="Nombres:"/>
                    <h:inputText id="nombres" value="#{profesor.profesor.nombres}" title="Nombres" required="true" requiredMessage="The nombres field is required." />
                    <h:outputText value="Apellidos:"/>
                    <h:inputText id="apellidos" value="#{profesor.profesor.apellidos}" title="Apellidos" required="true" requiredMessage="The apellidos field is required." />
                    <h:outputText value="Cedula:"/>
                    <h:inputText id="cedula" value="#{profesor.profesor.cedula}" title="Cedula" required="true" requiredMessage="The cedula field is required." />
                    <h:outputText value="Direccion:"/>
                    <h:inputText id="direccion" value="#{profesor.profesor.direccion}" title="Direccion" required="true" requiredMessage="The direccion field is required." />

                </h:panelGrid>
                <br />
                <h:commandLink action="#{profesor.edit}" value="Save">
                    <f:param name="jsfcrud.currentProfesor" value="#{jsfcrud_class['ec.edu.epn.libres.conexion.persistencia.dao.generico.test.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][profesor.profesor][profesor.converter].jsfcrud_invoke}"/>
                </h:commandLink>
                <br />
                <br />
                <h:commandLink action="#{profesor.detailSetup}" value="Show" immediate="true">
                    <f:param name="jsfcrud.currentProfesor" value="#{jsfcrud_class['ec.edu.epn.libres.conexion.persistencia.dao.generico.test.util.JsfUtil'].jsfcrud_method['getAsConvertedString'][profesor.profesor][profesor.converter].jsfcrud_invoke}"/>
                </h:commandLink>
                <br />
                <h:commandLink action="#{profesor.listSetup}" value="Show All Profesor Items" immediate="true"/>
                <br />
                <br />
                <h:commandLink value="Index" action="welcome" immediate="true" />

            </h:form>
        </body>
    </html>
</f:view>
